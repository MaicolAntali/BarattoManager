package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerListener;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerListener;

import java.util.Objects;

public class MainFrameController implements BaseController, RegisterControllerListener, ShowControllerListener {

	private final MainFrameModel model;
	private final MainFrameView view;

	public MainFrameController(MainFrameModel model, MainFrameView view) {
		this.model = Objects.requireNonNull(model);
		this.view = Objects.requireNonNull(view);

		view.setVersion(model.getVersion());
	}

	@Override
	public BaseModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@Override
	public void register(BaseController controller, String controllerName) {
		model.addNewController(controller, controllerName);
	}

	@Override
	public void show(String controllerName) {
		view.updateContentPanel(
				model.getControllerByName(controllerName)
						.orElseThrow()
						.getView()
						.getMainJPanel()
		);
	}
}
