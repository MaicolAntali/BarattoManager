package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

/**
 * Interface that represents menu actions
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
