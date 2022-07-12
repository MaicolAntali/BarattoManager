package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

/**
 * Interface that represent a Menu
 */
public interface Menu {
	/**
	 * Method used to create a {@link JMenuBar}
	 *
	 * @param user {@link User}
	 * @param tree {@link Tree}
	 * @return {@link JMenuBar}
	 */
	JMenuBar createMenu(User user, Tree tree);
}
