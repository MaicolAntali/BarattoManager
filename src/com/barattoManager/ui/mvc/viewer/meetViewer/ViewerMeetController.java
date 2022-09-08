package com.barattoManager.ui.mvc.viewer.meetViewer;

import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeController;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeModel;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeView;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handles the events of the {@link ViewerMeetView}
 */
public class ViewerMeetController implements Controller {

	private final ViewerMeetView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerMeetView} represent the view of the controller
	 */
	public ViewerMeetController(ViewerMeetView view) {
		this.view = view;

		var meetTreeController = new MeetTreeController(
				new MeetTreeModel(
						MeetManagerFactory.getManager().getAvailableMeet()
				),
				new MeetTreeView()
		);

		this.view.setTreePanel(meetTreeController.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_VIEWER.toString());
	}
}
