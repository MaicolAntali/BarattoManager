package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;

public interface MenuAction {
	void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user, ArticleTree articleTree);
}
