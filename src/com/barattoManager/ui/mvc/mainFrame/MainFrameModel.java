package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.login.LoginController;
import com.barattoManager.utils.AppConfigurator;

import java.util.HashMap;
import java.util.Optional;

/**
 * Model of {@link MainFrameController} that contains the data
 */
public class MainFrameModel implements Model {

	private final String version;
	private final HashMap<String, Controller> controllerHashMap;

	/**
	 * Constructor  of the class
	 */
	public MainFrameModel() {
		this.version = AppConfigurator.getInstance().getAppDataAsText("version");
		controllerHashMap = new HashMap<>();
	}

	/**
	 * Method used to get the version as a {@link String}
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Method used to add a new controller
	 * @param controller {@link Controller}
	 * @param controllerName used to search a controller by name
	 */
	public void addNewController(Controller controller, String controllerName) {
		if (this.controllerHashMap.get(controllerName) != null)
			this.controllerHashMap.remove(controllerName);

		this.controllerHashMap.put(controllerName.toLowerCase(), controller);
	}

	/**
	 * Method used to get a controller by name
	 * @param controllerName used to search a controller by name
	 * @return controller
	 */
	public Optional<Controller> getControllerByName(String controllerName) {
		return Optional.of(this.controllerHashMap.get(controllerName));
	}
}
