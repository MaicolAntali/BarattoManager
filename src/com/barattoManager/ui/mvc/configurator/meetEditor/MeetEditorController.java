package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.ui.action.actions.AddMeetAction;
import com.barattoManager.ui.action.actions.GoToControllerAction;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeController;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeModel;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeView;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handle the events of the {@link MeetEditorView}
 */
public class MeetEditorController extends GraspController {

	private final MeetEditorView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link MeetEditorView} represent the view of the controller
	 */
	public MeetEditorController(MeetEditorView view) {
		this.view = view;
		this.view.addActionNotifierListener(this);

		MeetTreeController meetTreeController = new MeetTreeController(
				new MeetTreeModel(
						MeetManagerFactory.getManager().getAvailableMeet()
				),
				new MeetTreeView()
		);
		this.view.setMeetTree(meetTreeController.getView().getMainJPanel());

		initAction();
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public MeetEditorView getView() {
		return view;
	}

	@Override
	protected void initAction() {
		addAction("Indietro", new GoToControllerAction(ControllerNames.HOMEPAGE_CONFIGURATOR));
		addAction("Aggiungi_nuovo_incontro", new AddMeetAction(view.getMainJPanel()));
	}
}
