package com.barattoManager.user;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.PasswordNotMatch;
import com.barattoManager.exception.UserAlreadyExist;
import com.barattoManager.exception.UserNotFound;
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
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to users(Configurator or User).
 */
public final class UserManager {
	public static final String ERROR_USER_ALREADY_EXIST = "L'utente %s esiste gia. Impossible crealo nuovamente";
	public static final String ERROR_NEW_USER_NULL_PARAM = "Impossibile stabilire quale istanza di User creare.";
	public static final String ERROR_USER_NOT_FOUND = "L'utente inserito non esiste. Riprovare";
	public static final String ERROR_PASSWORD_NOT_MATCH = "La password inserita non è coretta. Riprovare";

	private final File usersFile = new File(AppConfigurator.getInstance().getFileName("user_file"));
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final HashMap<String, User> userMap;

	private UserManager() {
		if (usersFile.exists()) {
			try {
				userMap = objectMapper.readValue(usersFile, new TypeReference<HashMap<String, User>>() {
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
						"Sono state impostate delle credenziali di base per il primo configuratore. \n\nUsername: Configurator\nPassword: %s".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
						"Credenziali Base",
						JOptionPane.INFORMATION_MESSAGE
				);
			} catch (UserAlreadyExist e) {
				e.printStackTrace();
			}
		}
	}

	private static final class UserManagerHolder {
		private static final UserManager instance = new UserManager();
	}

	/**
	 * Method used to crete get the {@link UserManager} instance.
	 * This method ues the lazy loading mechanism cause the inner class is loaded only if
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
	 */
	public void addNewUser(String username, String password, Boolean isAdmin) throws UserAlreadyExist {
		User user;
		if (userMap.containsKey(username.toLowerCase()))
			throw new UserAlreadyExist(ERROR_USER_ALREADY_EXIST.formatted(username));
		else {
			if (Objects.requireNonNull(isAdmin, ERROR_NEW_USER_NULL_PARAM)) {
				user = new Configurator(username, password);
			}
			else {
				user = new Viewer(username, password);
			}

			userMap.put(username.toLowerCase(), user);
			saveUserMapChange();
		}
	}

	/**
	 * Method used to check that a user’s (can be: {@link Configurator} or {@link Viewer}) credentials are valid
	 *
	 * @param username Username of the User
	 * @param password Password of the User
	 * @return {@link User} instance only if the password match
	 * @throws UserNotFound     is thrown if the {@code username} is not in the {@link #userMap}
	 * @throws PasswordNotMatch is thrown if the {@code password} don't match
	 */
	public User checkCredential(String username, String password) throws UserNotFound, PasswordNotMatch {
		User user = userMap.get(Objects.requireNonNull(username).toLowerCase());

		if (user == null)
			throw new UserNotFound(ERROR_USER_NOT_FOUND);
		else {
			if (user.getPassword().equals(password))
				return user;
			else
				throw new PasswordNotMatch(ERROR_PASSWORD_NOT_MATCH);
		}
	}

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
	 * Method used to save the {@link #userMap} changes in the Json ({@link #usersFile})
	 */
	private void saveUserMapChange() {
		try {
			objectMapper.writeValue(usersFile, userMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
