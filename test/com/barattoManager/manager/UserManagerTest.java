package com.barattoManager.manager;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserManagerTest {

	private static UserManager userManagerInstance;

	@BeforeAll
	static void setUp() {
		userManagerInstance = new UserManager(
				new JsonHandler<String, User>("test/json/users.json")
						.readJson(String.class, User.class)
		);
	}

	@Test
	void addUserWithoutName() {
		assertThrows(IllegalValuesException.class, () -> userManagerInstance.addNewUser("", "...", false));
	}

	@Test
	void addUserWithoutRole() {
		assertThrows(NullPointerException.class, () -> userManagerInstance.addNewUser("test", "...", null));
	}

	@Test
	void addExistingUser() {
		assertThrows(AlreadyExistException.class, () -> userManagerInstance.addNewUser("maicol", "...", false));
	}

	@Test
	void checkCredentialInvalidUser() {
		assertThrows(InvalidCredentialsException.class, () -> userManagerInstance.checkCredential("...", "..."));
	}

	@Test
	void checkCredentialInvalid() {
		assertThrows(InvalidCredentialsException.class, () -> userManagerInstance.checkCredential("Maicol", "wrongPwd"));
	}

	@Test
	void checkCredentialValid() {
		try {
			assertEquals("Configurator", userManagerInstance.checkCredential("Maicol", "12345").getChildType());
		} catch (InvalidCredentialsException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void addNewUser() {
		try {
			userManagerInstance.addNewUser("Test", "...", true);
		} catch (AlreadyExistException | IllegalValuesException e) {
			throw new RuntimeException(e);
		}

		assertEquals(2, userManagerInstance.getUserList().size());
	}

}