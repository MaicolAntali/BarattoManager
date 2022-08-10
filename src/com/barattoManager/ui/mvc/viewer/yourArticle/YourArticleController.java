package com.barattoManager.ui.mvc.viewer.yourArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuController;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuView;
import com.barattoManager.ui.utils.ControllerNames;

public class YourArticleController implements BaseController {

    private final YourArticleView view;

    public YourArticleController(YourArticleView view){
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

    @ActionListenerFor(sourceField = "questionButton")
    private void clickOnQuestionButton() {
        System.out.println("Informazioni");
    }
}
