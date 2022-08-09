package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeController;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeModel;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeView;
import com.barattoManager.ui.utils.ControllerNames;

public class MeetEditorController implements BaseController {

	private final MeetEditorView view;
	private final MeetTreeController meetTreeController;

	public MeetEditorController(MeetEditorView view) {
		this.view = view;

		meetTreeController = new MeetTreeController(
				new MeetTreeModel(
						MeetManagerFactory.getManager().getAvailableMeet()
				),
				new MeetTreeView()
		);

		this.view.setTreePanel(meetTreeController.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
	}

	@ActionListenerFor(sourceField = "addMeetButton")
	private void clickOnAddMeetButton() {
		System.out.println("Aggiungi nuovo incontro");
	}
}
