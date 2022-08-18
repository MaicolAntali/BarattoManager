package com.barattoManager.ui.mvc.tree.trade;

import com.barattoManager.services.trade.Trade;
import com.barattoManager.services.trade.TradeUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.TreeModel;
import com.barattoManager.ui.mvc.tree.TreeView;

public class TradeTreeController extends TreeController<Trade> {

	public TradeTreeController(TreeModel<Trade> model, TreeView<Trade> view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		TradeUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
