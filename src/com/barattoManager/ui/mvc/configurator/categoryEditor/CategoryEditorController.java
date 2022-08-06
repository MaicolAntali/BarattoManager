package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;

public class CategoryEditorController implements BaseController {

    private final CategoryEditorView view;

    public CategoryEditorController(CategoryEditorView view) {
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

    @ActionListenerFor(sourceField = "backToHome")
    private void clickOnBackToHome() {
        ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
    }

    @ActionListenerFor(sourceField = "addRootCategoryButton")
    private void clickAddRootCategoryButton() {
        System.out.println("Aggiungi categoria");
    }

    @ActionListenerFor(sourceField = "addSubCategoryButton")
    private void clickOnAddSubCategoryButton() {
        System.out.println("Aggiungi Sotto-Categoria");
    }

    @ActionListenerFor(sourceField = "addFieldButton")
    private void clickOnAddFieldButton() {
        System.out.println("Aggiungi field");
    }
}
