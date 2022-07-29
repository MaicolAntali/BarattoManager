package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.utils.AppConfigurator;

import java.util.HashMap;
import java.util.Optional;

public class MainFrameModel implements BaseModel {

	private final String version;
	private final HashMap<String, BaseController> controllerHashMap;

	public MainFrameModel() {
		this.version = AppConfigurator.getInstance().getAppDataAsText("version");
		controllerHashMap = new HashMap<>();
	}

	public String getVersion() {
		return version;
	}

	public void addNewController(BaseController controller, String controllerName) {
		this.controllerHashMap.put(controllerName.toLowerCase(), controller);
	}

	public Optional<BaseController> getControllerByName(String controllerName) {
		return Optional.of(this.controllerHashMap.get(controllerName));
	}
}
