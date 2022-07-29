package com.barattoManager.ui.mvc.event;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public interface RegisterControllerListener{

	void register(BaseController<? extends BaseModel, ? extends BaseView> controller, String controllerName);
}
