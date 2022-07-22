package com.barattoManager.manager;

import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.exception.InvalidCredentialsException;
import com.barattoManager.old.sample.user.User;
import ingsw.barattoManager.mvc.models.UserModel;
import ingsw.barattoManager.mvc.models.json.JsonHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserManagerTest {

	private static UserModel userManagerInstance;

	@BeforeAll
	static void setUp() {
		userManagerInstance = new UserModel(
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