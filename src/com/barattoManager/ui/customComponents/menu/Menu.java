package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

/**
 * Interface representing the menus
 */
public interface Menu {
	/**
	 * Method used to create a {@link JMenuBar}
	 *
	 * @param user {@link User} who has logged in
	 * @param tree {@link Tree} on which the actions of the menu will perform
	 * @return {@link JMenuBar} object
	 */
	JMenuBar createMenu(User user, Tree tree);
}
