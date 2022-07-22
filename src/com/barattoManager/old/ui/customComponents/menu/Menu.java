package com.barattoManager.old.ui.customComponents.menu;

import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.tree.Tree;

import javax.swing.*;

/**
 * Marker interface for any menu
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
