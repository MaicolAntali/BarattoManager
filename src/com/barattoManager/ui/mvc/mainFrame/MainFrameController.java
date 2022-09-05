package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerListener;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerListener;

import java.util.Objects;

public class MainFrameController implements Controller, RegisterControllerListener, ShowControllerListener {

	private final MainFrameModel model;
	private final MainFrameView view;

	public MainFrameController(MainFrameModel model, MainFrameView view) {
		this.model = Objects.requireNonNull(model);
		this.view = Objects.requireNonNull(view);

		view.setVersion(model.getVersion());
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void register(Controller controller, String controllerName) {
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
