package com.barattoManager.ui.customComponents.menu.actions;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.Tree;

public interface MenuAction {
	//FIXME: renderlo una classe astratta (https://refactoring.guru/design-patterns/command/java/example)
	void run(User user, Tree tree);
}
