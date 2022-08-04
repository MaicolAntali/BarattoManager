package com.barattoManager.services.user;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.InvalidCredentialException;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.utils.AppConfigurator;
import com.barattoManager.utils.SHA256;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

	private static final String ERROR_USER_ALREADY_EXIST = "L'utente %s esiste gia. Impossible crearlo nuovamente";
	private static final String POST_CONDITION_USER_NOT_IN_MAP = "Post-condition: The user is not present in the map.";
	private static final String SET_DEFAULT_CREDENTIALS = "Sono state impostate delle credenziali di base per il primo configuratore. \n\nUsername: Configurator\nPassword: %s";
	private final ConcurrentHashMap<String, User> userMap;

	/**
	 * Constructor of the class<br/>
	 * If the HashMap is empty then it inserts a default user and shows on the screen this operation
	 *
	 * @param userMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the users.
	 */
	public UserManager(ConcurrentHashMap<String, User> userMap) {
		this.userMap = userMap;

		if (userMap.isEmpty()) {
			try {
				addNewUser("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"), true);
			} catch (AlreadyExistException | InvalidArgumentException e) {
				e.printStackTrace();
			}

			new MessageDialogDisplay()
					.setMessage(SET_DEFAULT_CREDENTIALS.formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd")))
					.setTitle("Credenziali di Default")
					.show();
		}
	}


	public void addNewUser(String username, String password, Boolean isConfigurator) throws AlreadyExistException, InvalidArgumentException {
		if (username.isBlank() || password.isBlank())
			throw new InvalidArgumentException("Username o password non validi (stringa vuota o null).");

		if (userMap.containsKey(username.toLowerCase()))
			throw new AlreadyExistException(ERROR_USER_ALREADY_EXIST.formatted(username));


		userMap.put(username.toLowerCase(), new User(username, password, isConfigurator));
		UserUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.userMap);

		assert userMap.containsKey(username.toLowerCase()) : POST_CONDITION_USER_NOT_IN_MAP;

	}


	public User loginUser(String username, String password) throws InvalidCredentialException {
		User user = userMap.get(Objects.requireNonNull(username).toLowerCase());

		if (user == null)
			throw new InvalidCredentialException("Utente non trovato.");

		if (Objects.equals(user.getUsername(), username) && Arrays.equals(user.getPassword(), SHA256.hash(password)))
			return user;

		throw new InvalidCredentialException("Username e password non corrispondono.");
	}

	/**
	 * Method used to set a new password to a user
	 *
	 * @param user     {@link User} to set new password
	 * @param password New Password to set
	 */
	public void setUserPassword(User user, String password) {
		user.setPassword(password);
		UserUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.userMap);
	}

	/**
	 * Method used to get the {@link List} with all {@link User users}
	 *
	 * @return {@link List} with all {@link User users}
	 */
	public List<User> getUserList() {
		return userMap.values().stream().toList();
	}
}
