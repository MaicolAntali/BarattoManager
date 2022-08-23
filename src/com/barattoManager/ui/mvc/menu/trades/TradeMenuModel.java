package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.TreeController;

public class TradeMenuModel implements BaseModel {
	private final TreeController<?> treeController;

	public TradeMenuModel(TreeController<?> treeController) {
		this.treeController = treeController;
	}

	public TreeController<?> getTreeController() {
		return treeController;
	}
}
