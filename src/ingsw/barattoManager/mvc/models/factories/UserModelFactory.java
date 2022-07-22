package ingsw.barattoManager.mvc.models.factories;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.utils.AppConfigurator;
import ingsw.barattoManager.mvc.models.UserModel;
import ingsw.barattoManager.mvc.models.json.JsonHandler;

/**
 * Class that constructs the {@link UserModel}<br/>
 * {@link UserModel}is declared in the class as a static constant, to ensure one instance for the whole project.
 */
public class UserModelFactory {

	private static final UserModel USER_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, User>(
				AppConfigurator.getInstance().getFileName("users")
		);

		EventFactory.getUsersEvent().addListener(jsonHandler);

		USER_MANAGER = new UserModel(jsonHandler.readJson(String.class, User.class));
	}

	/**
	 * Method used to get the instance of {@link UserModel} stored in the class.
	 *
	 * @return The {@link UserModel} object
	 */
	public static UserModel getManager() {
		return USER_MANAGER;
	}
}
