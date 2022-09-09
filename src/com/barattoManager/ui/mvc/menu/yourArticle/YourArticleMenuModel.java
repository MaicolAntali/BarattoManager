package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.TreeController;

/**
 * Model of {@link YourArticleMenuController} that contains the data
 */
public class YourArticleMenuModel implements Model {

	private final TreeController<?> treeController;

	/**
	 * Constructor of the class
	 *
	 * @param treeController {@link TreeController}
	 */
	public YourArticleMenuModel(TreeController<?> treeController) {
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
