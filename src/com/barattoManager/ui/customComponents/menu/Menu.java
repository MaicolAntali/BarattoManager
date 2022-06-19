package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

public interface Menu  {
	JMenuBar createMenu(RepaintEventHandler repaintEventHandler, User user, Tree tree);
}
