package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

public interface MenuAction {
	void run(RepaintEventHandler repaintEventHandler, User user, Tree tree);
}
