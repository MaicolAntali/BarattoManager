package com.barattoManager.ui.customComponents.menu.yourArticle.actions;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.user.User;

import javax.swing.*;

public interface MenuItemAction {
	void run(JPanel fatherPanel, RepaintEventHandler repaintEventHandler, User user);
}
