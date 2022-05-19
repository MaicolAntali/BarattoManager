package com.barattoManager.user;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.user.configurator.Configurator;
import com.barattoManager.user.viewer.Viewer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the users(Configurator or User).
 */
public final class UserManager {
	/**
	 * User already exist error
	 */
	private static final String ERROR_USER_ALREADY_EXIST = "L'utente %s esiste gia. Impossible crearlo nuovamente";
	/**
	 * User not found error
	 */
	private static final String ERROR_USER_NOT_FOUND = "L'utente inserito non esiste. Riprovare";
	/**
	 * Password not match error
	 */
	private static final String ERROR_PASSWORD_NOT_MATCH = "La password inserita non è coretta. Riprovare";
	/**
	 * Post-condition: The user is not present in the map.
	 */
	public static final String POST_CONDITION_USER_NOT_IN_MAP = "Post-condition: The user is not present in the map.";
	/**
	 * Invalid Username error
	 */
	private static final String ERROR_INVALID_USERNAME = "Lo username non è valido";
	/**
	 * Message to show the first configurator credentials
	 */
	private static final String FIRST_CONFIGURATOR_CREDENTIALS = "Sono state impostate delle credenziali di base per il primo configuratore. \n\nUsername: Configurator\nPassword: %s";
	/**
	 * Basic credentials
	 */
	private static final String DEFAULT_CREDENTIALS = "Credenziali Base";
	/**
	 * Impossible establish user instance message
	 */
	private static final String MESSAGE_IMPOSSIBLE_ESTABLISH_USER_INSTANCE = "Impossibile stabilire quale istanza di User creare.";

	/**
	 * User JSON file
	 */
	private final File usersFile = new File(AppConfigurator.getInstance().getFileName("user_file"));
	/**
	 * {@link ObjectMapper} object, used to parse JSON
	 */
	private final ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * {@link HashMap} user map
	 */
	private final HashMap<String, User> userMap;

	/**
	 * {@link UserManager} constructor
	 */
	private UserManager() {
		if (usersFile.exists()) {
			try {
				userMap = objectMapper.readValue(usersFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			userMap = new HashMap<>();
			try {
				addNewUser("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"), true);
				JOptionPane.showMessageDialog(
						null,
						FIRST_CONFIGURATOR_CREDENTIALS.formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
						DEFAULT_CREDENTIALS,
						JOptionPane.INFORMATION_MESSAGE
				);
			} catch (AlreadyExistException | IllegalValuesException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Holder class of instance
	 */
	private static final class UserManagerHolder {
		/**
		 * Instance of {@link UserManager}
		 */
		private static final UserManager instance = new UserManager();
	}


	/**
	 * Method used to create get the {@link UserManager} instance.
	 * This method use the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code getInstance()} method is called.
	 * Also is thread safe cause every thread read the same {@link UserManager} instance.
	 *
	 * @return The Instance of {@link UserManager} class
	 */
	public static UserManager getInstance() {
		return UserManagerHolder.instance;
	}

	/**
	 * Method used to add a new {@link User} in to {@link #userMap}<br/>
	 * This method can select which type of user to create:
	 * <ul>
	 *     <li>{@link Configurator} -> If the {@code isAdmin} is {@code true}</li>
	 *     <li>{@link Viewer} -> If the {@code isAdmin} is {@code false}</li>
	 * </ul>
	 * After adding the User in the {@code HashMap<String, User> userMap}, the method calls {@link #saveUserMapChange()}
	 * for save the last change in the json file.
	 *
	 * @param username Username of the User
	 * @param password Password of the User
	 * @param isAdmin  Indicates whether a User is an admins or not.
	 * @throws AlreadyExistException Is thrown if the new user that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the username is empty
	 */
	public void addNewUser(String username, String password, Boolean isAdmin) throws AlreadyExistException, IllegalValuesException {
		if (!username.isBlank()) {
			if (userMap.containsKey(username.toLowerCase()))
				throw new AlreadyExistException(ERROR_USER_ALREADY_EXIST.formatted(username));
			else {
				User user;
				if (Objects.requireNonNull(isAdmin, MESSAGE_IMPOSSIBLE_ESTABLISH_USER_INSTANCE)) {
					user = new Configurator(username, password);
				}
				else {
					user = new Viewer(username, password);
				}

				userMap.put(username.toLowerCase(), user);
				saveUserMapChange();

				assert userMap.containsKey(username) : POST_CONDITION_USER_NOT_IN_MAP;
			}
		}
		else {
			throw new IllegalValuesException(ERROR_INVALID_USERNAME);
		}

	}

	/**
	 * Method used to check that a user’s (can be: {@link Configurator} or {@link Viewer}) credentials are valid
	 *
	 * @param username Username of the User
	 * @param password Password of the User
	 * @return {@link User} instance only if the password match
	 * @throws InvalidCredentialsException Is thrown if the {@code username} is not in the {@link #userMap} and/or the {@code password} doesn't match
	 */
	public User checkCredential(String username, String password) throws InvalidCredentialsException {
		User user = userMap.get(Objects.requireNonNull(username).toLowerCase());

		if (user == null)
			throw new InvalidCredentialsException(ERROR_USER_NOT_FOUND);
		else {
			if (user.getPassword().equals(password))
				return user;
			else
				throw new InvalidCredentialsException(ERROR_PASSWORD_NOT_MATCH);
		}
	}

	/**
	 * Method used to set password of a User
	 * @param user User object to set the new password
	 * @param password New Password
	 */
	public void setUserPassword(User user, String password) {
		user.setPassword(password);
		saveUserMapChange();
	}

	/**
	 * Method used to get the {@link #userMap}
	 *
	 * @return The {@link #userMap}
	 */
	public HashMap<String, User> getUserMap() {
		return userMap;
	}

	/**
	 * Method used to save the {@link #userMap} changes in the Json file({@link #usersFile})
	 */
	private void saveUserMapChange() {
		try {
			objectMapper.writeValue(usersFile, userMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
