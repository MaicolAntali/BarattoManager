package com.barattoManager.ui.mvc.menu.action.actions;

import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.menu.action.BaseAction;
import com.barattoManager.ui.mvc.tree.TreeController;

public class NewArticleAction extends BaseAction {

	private final User user;
	private final TreeController<?> treeController;

	public NewArticleAction(User user, TreeController<?> treeController) {
		this.user = user;
		this.treeController = treeController;
	}

	@Override
	public void run() {
		//TODO: Implements the run Method
	}
}
