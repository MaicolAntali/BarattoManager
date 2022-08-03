package com.barattoManager.ui.mvc.login;

import com.barattoManager.exception.InvalidCredentialException;
import com.barattoManager.services.Store;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class LoginController implements BaseController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        ActionListenerInstaller.processAnnotations(this, view);
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

    @ActionListenerFor(sourceField = "abortButton")
    private void clickOnAbort() {
        ShowControllerHandlerFactory.getHandler().fireShowListeners("homepage");
    }

    @ActionListenerFor(sourceField = "loginButton")
    private void clickOnLogin() {
        try {
           var user = UserManagerFactory.getManager()
                    .loginUser(model.getUsername(), model.getPassword());

            Store.setLoggedUser(user);

        } catch (InvalidCredentialException e) {
            new MessageDialogDisplay()
                    .setParentComponent(view.getMainJPanel())
                    .setMessageType(JOptionPane.ERROR_MESSAGE)
                    .setTitle("Errore")
                    .setMessage(e.getMessage())
                    .show();
        }

        if (Store.getLoggedUser().isPasswordValid())
            System.out.println("Cambiare PWD");

        UserManagerFactory.getManager().setUserPassword(
                Store.getLoggedUser(), "nuovaPWD"
        );

        System.out.println("LOGIN ESEGUITO");
    }

    @DocumentListenerFor(sourceField = "usernameField")
    private void usernameHasChange() {
        model.setUsername(view.getUsername());
    }

    @DocumentListenerFor(sourceField = "passwordField")
    private void passwordHasChange() {
        model.setPassword(view.getPassword());
    }

}