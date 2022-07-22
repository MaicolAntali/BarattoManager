package ingsw.barattoManager.mvc.application.homepage;

import com.barattoManager.old.ui.components.ComponentsName;
import ingsw.barattoManager.mvc.application.login.LoginUi;
import ingsw.barattoManager.mvc.application.main.ApplicationUi;
import ingsw.barattoManager.mvc.controller.Controller;
import ingsw.barattoManager.mvc.event.BaseEventHandler;
import ingsw.barattoManager.mvc.event.Event;
import ingsw.barattoManager.mvc.event.ShowEventHandler;
import ingsw.barattoManager.mvc.optionPanels.registerNewUser.RegisterNewUserController;
import ingsw.barattoManager.mvc.optionPanels.registerNewUser.RegisterNewUserPanel;

import java.util.ArrayList;
import java.util.List;

public class HomepageController extends Controller {

	private final HomepageUi view;

	public HomepageController(HomepageUi view) {
		this.view = view;

		registerAction(
				this.view.getLoginButton(),
				() -> {
					// FIXME: rimuovere i null
					var loginUi = new LoginUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, null, null);
					ShowEventHandler.fireListener(
							new Event(
									"AddNewPanel",
									new ArrayList<>(List.of(loginUi, ComponentsName.LOGIN.toString()))
							)
					);

					//TODO: Controller login

					ShowEventHandler.fireListener(
							new Event(
									"ShowPanel",
									new ArrayList<>(List.of(ComponentsName.LOGIN.toString()))
							)
					);
				}
		);
		registerAction(
				this.view.getRegisterButton(),
				() -> {
					var eventHandler = new BaseEventHandler();
					var registerNewUserController = new RegisterNewUserController(
							new RegisterNewUserPanel(this.view, false, eventHandler),
							this
					);

					eventHandler.addListeners(registerNewUserController);
					eventHandler.fireListener(new Event("Show_UI"));
				}
		);
	}

	public HomepageUi getView() {
		return view;
	}
}
