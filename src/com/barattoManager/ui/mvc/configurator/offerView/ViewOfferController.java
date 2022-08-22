package com.barattoManager.ui.mvc.configurator.offerView;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.StoreTreeModel;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeController;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeModel;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeView;
import com.barattoManager.ui.utils.ControllerNames;

public class ViewOfferController implements BaseController {

    private final ViewOfferView view;

    public ViewOfferController(ViewOfferView view) {
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
}
