package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

/**
 * Menu Action
 */
public interface MenuAction {
	/**
	 * Method used to run the menu action
	 *
	 * @param user {@link User}
	 * @param tree {@link Tree}
	 */
	void run(User user, Tree tree);
}
