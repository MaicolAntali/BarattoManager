package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.event.RegisterControllerListener;

public class MainFrameController extends BaseController<MainFrameModel, MainFrameView> implements RegisterControllerListener {

	public MainFrameController(MainFrameModel model, MainFrameView view) {
		super(model, view);

		getView().setVersion(getModel().getVersion());
	}

	@Override
	public void register(BaseController<? extends BaseModel, ? extends BaseView> controller, String controllerName) {
		getModel().addNewController(controller, controllerName);
	}
}
