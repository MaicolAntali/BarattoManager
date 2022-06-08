package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;

/**
 * Interface that represent action items menu
 */
public interface MenuItemAction {
	void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree);
}
