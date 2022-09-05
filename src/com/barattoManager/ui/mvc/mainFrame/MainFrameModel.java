package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.utils.AppConfigurator;

import java.util.HashMap;
import java.util.Optional;

public class MainFrameModel implements Model {

	private final String version;
	private final HashMap<String, Controller> controllerHashMap;

	public MainFrameModel() {
		this.version = AppConfigurator.getInstance().getAppDataAsText("version");
		controllerHashMap = new HashMap<>();
	}

	public String getVersion() {
		return version;
	}

	public void addNewController(Controller controller, String controllerName) {
		if (this.controllerHashMap.get(controllerName) != null)
			this.controllerHashMap.remove(controllerName);

		this.controllerHashMap.put(controllerName.toLowerCase(), controller);
	}

	public Optional<Controller> getControllerByName(String controllerName) {
		return Optional.of(this.controllerHashMap.get(controllerName));
	}
}
