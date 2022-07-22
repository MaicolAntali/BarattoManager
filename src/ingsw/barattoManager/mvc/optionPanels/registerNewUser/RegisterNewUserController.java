package ingsw.barattoManager.mvc.optionPanels.registerNewUser;

import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.utils.AppConfigurator;
import ingsw.barattoManager.mvc.application.homepage.HomepageController;
import ingsw.barattoManager.mvc.controller.Controller;
import ingsw.barattoManager.mvc.models.UserModel;
import ingsw.barattoManager.mvc.models.factories.UserModelFactory;

public class RegisterNewUserController extends Controller {

	private final UserModel model = UserModelFactory.getManager();

	public RegisterNewUserController(RegisterNewUserPanel view, HomepageController homepageController) {
		registerAction("Show_UI", view::showUI);

		registerAction("JOptionPane_OK", () -> {
			System.out.println(view.getUsernameField().getText());

			try {
				model.addNewUser(
						view.getUsernameField().getText(),
						AppConfigurator.getInstance().getPasswordSetting("default_pwd"),
						false
				);
			} catch (IllegalValuesException | AlreadyExistException e) {
				view.showErrorMessage(homepageController.getView(), e.getMessage());
				return;
			}

			view.showSuccesMessage(
					homepageController.getView(),
					"Nuovo fruitore creato correttamente.\n\nUsername: %s\nPassword: %s".formatted(
							view.getUsernameField().getText(),
							AppConfigurator.getInstance().getPasswordSetting("default_pwd")
					),
					"Registrazione Completata"
			);
		});
	}
}
