package com.barattoManager.user;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.PasswordNotMatch;
import com.barattoManager.exception.UserAlreadyExist;
import com.barattoManager.exception.UserNotFound;
import com.barattoManager.user.configurator.Configurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
	private UserManager instance;

	@BeforeEach
	void setUp() {
		instance = UserManager.getInstance();
	}

	@Test
	void addNewUserInvalid() {
		assertThrows(NullPointerException.class, () -> instance.addNewUser("test", "test", null));
	}

	@Test
	void isUserMapNotEmpty() {
		assertFalse(instance.getUserMap().isEmpty());
	}

	@Test
	void mapContainsDefaultConfigurator() {
		var users = instance.getUserMap().values();
		assertTrue(
				users.stream()
						.anyMatch(user -> user.getUsername().equals("Configurator"))
		);
	}

	@Test
	void addAlreadyExistUser() {
		assertThrows(UserAlreadyExist.class, () -> instance.addNewUser("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"), false));
	}

	@Test
	void userNotFound() {
		assertThrows(UserNotFound.class, () -> instance.checkCredential("NotValidUsername", AppConfigurator.getInstance().getPasswordSetting("default_pwd")));
	}

	@Test
	void userWrongPassword() {
		assertThrows(PasswordNotMatch.class, () -> instance.checkCredential("Configurator", "WrongPsw"));
	}

	@Test
	void userCorrectPassword() {
		User user;
		try {
			user = instance.checkCredential("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
		} catch (UserNotFound | PasswordNotMatch e) {
			throw new RuntimeException(e);
		}

		assertInstanceOf(Configurator.class, user);
		assertEquals(user.getUsername(), "Configurator");
		assertEquals(user.getPassword(), AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	}
}