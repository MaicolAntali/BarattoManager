package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.utils.AppConfigurator;

import java.util.HashMap;

public class MainFrameModel implements BaseModel {

	private final String version;
	private final HashMap<String, BaseController<? extends BaseModel, ? extends BaseView>> controllerHashMap;

	public MainFrameModel() {
		this.version = AppConfigurator.getInstance().getAppDataAsText("version");
		controllerHashMap = new HashMap<>();
	}

	public String getVersion() {
		return version;
	}

	public void addNewController(BaseController<? extends BaseModel, ? extends BaseView> controller, String controllerName) {
		this.controllerHashMap.put(controllerName.toLowerCase(), controller);
	}

}
