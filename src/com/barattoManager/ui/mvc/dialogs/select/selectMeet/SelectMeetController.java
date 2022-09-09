package com.barattoManager.ui.mvc.dialogs.select.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;

/**
 * Controller that handle the events of the {@link SelectView} and update the data in the {@link SelectMeetModel}
 */
public class SelectMeetController extends GraspController {

	private final SelectMeetModel model;
	private final SelectView<Meet> view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link SelectMeetModel} represent the model of the controller
	 * @param view  {@link SelectView} represent the view of the controller
	 */
	public SelectMeetController(SelectMeetModel model, SelectView<Meet> view) {
		this.model = model;
		this.view = view;

		initAction();

		this.view.addActionNotifierListener(this);
		this.view.draw(this.model.getMeets());
	}

	@Override
	public SelectMeetModel getModel() {
		return model;
	}

	@Override
	public SelectView<Meet> getView() {
		return view;
	}

	@Override
	protected void initAction() {
		addAction("comboBoxChanged", () -> model.setMeetSelected(view.getSelectedObject()));
	}
}
