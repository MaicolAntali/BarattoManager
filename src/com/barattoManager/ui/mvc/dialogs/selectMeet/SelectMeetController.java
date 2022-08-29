package com.barattoManager.ui.mvc.dialogs.selectMeet;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class SelectMeetController implements BaseController {

	private final SelectMeetModel model;
	private final SelectMeetView view;

	public SelectMeetController(SelectMeetModel model, SelectMeetView view) {
		this.model = model;
		this.view = view;

		this.view.draw(this.model.getMeets());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public SelectMeetModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "meetJComboBox")
	private void comboBoxValueHasChanged() {
		model.setMeetSelected(view.getSelectedMeet());
	}
}