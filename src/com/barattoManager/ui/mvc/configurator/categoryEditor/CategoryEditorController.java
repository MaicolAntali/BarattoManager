package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeView;
import com.barattoManager.ui.utils.ControllerNames;

public class CategoryEditorController implements BaseController {

    private final CategoryEditorView view;

    public CategoryEditorController(CategoryEditorView view) {
        this.view = view;

        ActionListenerInstaller.processAnnotations(this, view);

        var categoryTreeController = new CategoryTreeController(
                new CategoryTreeModel(
                        CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()
                ),
                new CategoryTreeView()
        );

        this.view.setTreePanel(categoryTreeController.getView().getMainJPanel());
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
       //TODO: ADD ROOT CATEGORY
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
