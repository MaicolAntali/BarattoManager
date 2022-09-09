package com.barattoManager.ui.mvc.menu.storeArticle;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleController;
import com.barattoManager.ui.mvc.tree.TreeController;

/**
 * Model of {@link StoreArticleMenuController} that contains the data
 */
public class StoreArticleMenuModel implements Model {

	private final TreeController<?> treeController;

	/**
	 * Constructor of the class
	 * @param treeController {@link TreeController}
	 */
	public StoreArticleMenuModel(TreeController<?> treeController) {
		this.treeController = treeController;
	}

	/**
	 * Method used to get a tree controller
	 * @return tree controller
	 */
	public TreeController<?> getTreeController() {
		return treeController;
	}
}
