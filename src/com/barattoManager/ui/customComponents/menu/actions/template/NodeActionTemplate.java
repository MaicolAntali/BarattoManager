package com.barattoManager.ui.customComponents.menu.actions.template;

import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public abstract class NodeActionTemplate implements MenuAction {

	@Override
	public void run(User user, Tree tree) {
		TreeNode[] nodePath;
		try {
			nodePath = tree.getSelectedPathNode();
		} catch (NoNodeSelected e) {
			JOptionPane.showMessageDialog(tree, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}


		customAction(nodePath, tree, user);
	}

	protected abstract void customAction(TreeNode[] nodePath, Tree tree, User user);
}
