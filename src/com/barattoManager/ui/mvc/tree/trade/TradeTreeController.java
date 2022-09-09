package com.barattoManager.ui.mvc.tree.trade;

import com.barattoManager.services.trade.Trade;
import com.barattoManager.services.trade.TradeUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.TreeModel;
import com.barattoManager.ui.mvc.tree.TreeView;

/**
 * Controller that handle the events of the {@link TreeView} and update the data in the {@link TreeModel}
 */
public class TradeTreeController extends TreeController<Trade> {

	/**
	 * Constructor of the class
	 *
	 * @param model {@link TreeModel} represent the model of the controller
	 * @param view  {@link  TreeView} represent the view of the controller
	 */
	public TradeTreeController(TreeModel<Trade> model, TreeView<Trade> view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		TradeUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
