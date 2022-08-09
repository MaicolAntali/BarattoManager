package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class NewMeetController implements BaseController {

    private final NewMeetModel model;
    private final NewMeetView view;

    public NewMeetController(NewMeetModel model, NewMeetView view) {
        this.model = model;
        this.view = view;

        DocumentListenerInstaller.processAnnotations(this, model);
    }

    @Override
    public NewMeetModel getModel() {
        return model;
    }

    @Override
    public BaseView getView() {
        return view;
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
