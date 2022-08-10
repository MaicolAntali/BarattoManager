package com.barattoManager.ui.mvc.viewer.categoryView;

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

public class ViewCategoryController implements BaseController {

    private final ViewCategoryView view;

    public ViewCategoryController(ViewCategoryView view) {
        this.view = view;

        var categoryTreeController = new CategoryTreeController(
                new CategoryTreeModel(
                        CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()
                ),
                new CategoryTreeView()
        );

        this.view.setTreePanel(categoryTreeController.getView().getMainJPanel());

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
        ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_VIEWER.toString());
    }
}
