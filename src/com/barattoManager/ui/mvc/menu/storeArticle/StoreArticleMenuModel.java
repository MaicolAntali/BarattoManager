package com.barattoManager.ui.mvc.menu.storeArticle;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.TreeController;

public class StoreArticleMenuModel implements Model {

	private final TreeController<?> treeController;

	public StoreArticleMenuModel(TreeController<?> treeController) {
		this.treeController = treeController;
	}

	public TreeController<?> getTreeController() {
		return treeController;
	}
}
