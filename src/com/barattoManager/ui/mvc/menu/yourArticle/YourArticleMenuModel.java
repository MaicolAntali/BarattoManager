package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.TreeController;

public class YourArticleMenuModel implements BaseModel {

	private final TreeController<?> treeController;

	public YourArticleMenuModel(TreeController<?> treeController) {
		this.treeController = treeController;
	}

	public TreeController<?> getTreeController() {
		return treeController;
	}
}
