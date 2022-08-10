package com.barattoManager.ui.mvc.viewer.articleViewer;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuController;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuView;
import com.barattoManager.ui.utils.ControllerNames;

public class ViewerArticleController implements BaseController {

    private final ViewerArticleView view;

    public ViewerArticleController(ViewerArticleView view){
        this.view = view;

        var yourArticleMenu = new YourArticleMenuController(new YourArticleMenuView());
        this.view.setMenuPanel(yourArticleMenu.getView().getMainJPanel());

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

    @ActionListenerFor(sourceField = "loadJsonQuestionButton")
    private void clickOnLoadJsonQuestionButton() {
        System.out.println("Informazioni");
    }
}
