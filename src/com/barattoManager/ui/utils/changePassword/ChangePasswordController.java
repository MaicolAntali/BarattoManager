package com.barattoManager.ui.utils.changePassword;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public class ChangePasswordController implements BaseController {

    private final ChangePasswordModel model;
    private final ChangePasswordView view;

    public ChangePasswordController(ChangePasswordModel model, ChangePasswordView view) {
        this.model = model;
        this.view = view;

        DocumentListenerInstaller.processAnnotations(this, view);
    }

    @Override
    public BaseModel getModel() {
        return model;
    }

    @Override
    public BaseView getView() {
        return view;
    }

    @DocumentListenerFor(sourceField = "passwordField")
    public void passwordHasChange() {
        model.setPassword(view.getPassword());
    }

    public String getPassword() {
        return model.getPassword();
    }
}
