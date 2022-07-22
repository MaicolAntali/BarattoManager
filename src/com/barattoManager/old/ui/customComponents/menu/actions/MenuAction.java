package com.barattoManager.old.ui.customComponents.menu.actions;

import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.tree.Tree;

/**
 * Marker interface for any menu action
 */
public interface MenuAction {

	/**
	 * Method used to run the menu action
	 *
	 * @param user {@link User} who has logged in
	 * @param tree {@link Tree} on which the actions of the menu will perform
	 */
	void run(User user, Tree tree);
}
