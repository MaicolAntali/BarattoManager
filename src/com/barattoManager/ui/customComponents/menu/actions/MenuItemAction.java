package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;

/**
 * Interface that represent action items menu
 */
public interface MenuItemAction {
	/**
	 * Run method
	 * @param fatherPanel {@link JPanel}
	 * @param repaintEventHandler {@link RepaintEventHandler} object
	 * @param user {@link User} object
	 * @param articleTree {@link ArticleTree} object
	 */
	void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree);
}