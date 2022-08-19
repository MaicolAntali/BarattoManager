package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

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
        System.out.println("Accetta Scambio");
    }

    @ActionListenerFor(sourceField = "acceptRescheduleAction")
    private void clickOnAcceptRescheduleAction() {
        System.out.println("Accetta Ma Riprogramma");
    }
}
