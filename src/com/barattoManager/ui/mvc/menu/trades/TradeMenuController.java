package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.services.Store;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.menu.action.actions.AcceptRescheduleTradeAction;
import com.barattoManager.ui.mvc.menu.action.actions.AcceptTradeAction;

/**
 * Controller that handle the events of the {@link TradeMenuView} and update the data in the {@link TradeMenuModel}
 */
public class TradeMenuController implements Controller {

	private final TradeMenuModel model;
	private final TradeMenuView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link TradeMenuModel} represent the model of the controller
	 * @param view  {@link TradeMenuView} represent the view of the controller
	 */
	public TradeMenuController(TradeMenuModel model, TradeMenuView view) {
		this.model = model;
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
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
