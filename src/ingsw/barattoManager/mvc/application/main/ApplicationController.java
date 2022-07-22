package ingsw.barattoManager.mvc.application.main;

import ingsw.barattoManager.mvc.event.ShowEventHandler;
import ingsw.barattoManager.mvc.event.ShowEventListener;

import java.awt.*;

public class ApplicationController implements ShowEventListener {

	private final ApplicationUi view;

	public ApplicationController(ApplicationUi view) {
		this.view = view;
		ShowEventHandler.addListeners(this);
	}

	@Override
	public void addNewPanel(Component component, String componentName) {
		view.getPanelContainer().add(component, componentName);
	}

	@Override
	public void showPanel(String componentName) {
		view.getCardLayout().show(view.getPanelContainer(), componentName);
	}
}
