package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;

/**
 * Controller that handle the events of the {@link NewMeetView} and update the data in the {@link NewMeetModel}
 */
public class NewMeetController implements Controller, DaysCheckboxListener {

	private final NewMeetModel model;
	private final NewMeetView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link NewMeetModel} represent the model of the controller
	 * @param view {@link NewMeetView} represent the view of the controller
	 */
	public NewMeetController(NewMeetModel model, NewMeetView view) {
		this.model = model;
		this.view = view;

		this.view.addDaysCheckboxListener(this);
		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewMeetModel getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void daysUpdate(String day, boolean selected) {
		if (selected)
			model.getDays().add(day);
		else
			model.getDays().remove(day);
	}

	@DocumentListenerFor(sourceField = "cityField")
	private void cityHasChanged() {
		model.setCity(view.getCityField());
	}

	@DocumentListenerFor(sourceField = "squareField")
	private void squareHasChanged() {
		model.setSquare(view.getSquareField());
	}

	@DocumentListenerFor(sourceField = "startTimeField")
	private void startTimeHasChanged() {
		model.setStartTime(view.getStartTimeField());
	}

	@DocumentListenerFor(sourceField = "endTimeField")
	private void endTimeHasChanged() {
		model.setEndTime(view.getEndTimeField());
	}

	@DocumentListenerFor(sourceField = "daysBeforeExpireField")
	private void daysBeforeExpireHasChanged() {
		model.setDaysBeforeExpire(view.getDaysBeforeExpireField());
	}
}
