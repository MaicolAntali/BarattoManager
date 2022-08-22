package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.services.Store;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.menu.action.actions.AcceptRescheduleTradeAction;
import com.barattoManager.ui.mvc.menu.action.actions.AcceptTradeAction;

public class TradeMenuController implements BaseController {

    private final TradeMenuModel model;
    private final TradeMenuView view;

    public TradeMenuController(TradeMenuModel model, TradeMenuView view) {
        this.model = model;
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

    @ActionListenerFor(sourceField = "acceptTradeAction")
    private void clickOnAcceptTradeAction() {
        new AcceptTradeAction(Store.getLoggedUser(), model.getTreeController()).run();
    }

    @ActionListenerFor(sourceField = "acceptRescheduleAction")
    private void clickOnAcceptRescheduleAction() {
      new AcceptRescheduleTradeAction(Store.getLoggedUser(), model.getTreeController()).run();
    }
}
