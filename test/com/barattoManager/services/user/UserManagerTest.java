package com.barattoManager.services.user;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.InvalidCredentialException;
import com.barattoManager.services.json.JsonHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
		assertThrows(InvalidArgumentException.class, () -> userManagerInstance.addNewUser("", "...", false));
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
		assertThrows(InvalidCredentialException.class, () -> userManagerInstance.loginUser("...", "..."));
	}

	@Test
	void checkCredentialInvalid() {
		assertThrows(InvalidCredentialException.class, () -> userManagerInstance.loginUser("Maicol", "wrongPwd"));
	}

	@Test
	void checkCredentialValid() {
		try {
			assertTrue(userManagerInstance.loginUser("Maicol", "12345").isConfigurator());
		} catch (InvalidCredentialException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void addNewUser() {
		try {
			userManagerInstance.addNewUser("Test", "...", true);
		} catch (AlreadyExistException | InvalidArgumentException e) {
			throw new RuntimeException(e);
		}

		assertEquals(2, userManagerInstance.getUsers().size());
	}

}