package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

public interface MenuAction {
	void run(User user, Tree tree);
}
