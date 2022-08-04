package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.register.RegisterController;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;
import com.barattoManager.ui.utils.ControllerNames;

public class ConfiguratorHomepageController implements BaseController {

    private final ConfiguratorHomepageView view;

    public ConfiguratorHomepageController(ConfiguratorHomepageView view) {
        this.view = view;

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

    @ActionListenerFor(sourceField = "configCategoryButton")
    private void clickOnConfigCategoryButton(){
        System.out.println("Configura categorie");
    }

    @ActionListenerFor(sourceField = "configMeetButton")
    private void clickOnConfigMeetButton(){
        System.out.println("Configura incontro");
    }

    @ActionListenerFor(sourceField = "addNewConfigurator")
    private void clickOnAddNewConfigurator(){
        RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
                new RegisterController(new RegisterModel(true), new RegisterView()), ControllerNames.REGISTER_CONFIGURATOR.toString()
        );
        ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.REGISTER_CONFIGURATOR.toString());
    }

    @ActionListenerFor(sourceField = "showOffer")
    private void clickOnShowOffer(){
        System.out.println("Mostra offerte");
    }

    @ActionListenerFor(sourceField = "loadJsonButton")
    private void clickOnLoadJsonButton(){
        System.out.println("Carica file");
    }

    @ActionListenerFor(sourceField = "loadJsonQuestionButton")
    private void clickOnLoadJsonQuestionButton(){
        System.out.println("Informazioni");
    }
}