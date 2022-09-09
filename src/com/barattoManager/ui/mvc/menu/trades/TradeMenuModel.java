package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.TreeController;

/**
 * Model of {@link TradeMenuController} that contains the data
 */
public class TradeMenuModel implements Model {
	private final TreeController<?> treeController;

	/**
	 * Constructor of the class
	 *
	 * @param treeController {@link TreeController}
	 */
	public TradeMenuModel(TreeController<?> treeController) {
		this.treeController = treeController;
	}

	/**
	 * Method used to get a tree controller
	 *
	 * @return tree controller
	 */
	public TreeController<?> getTreeController() {
		return treeController;
	}
}
