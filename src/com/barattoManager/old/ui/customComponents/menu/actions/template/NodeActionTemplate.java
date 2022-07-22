package com.barattoManager.old.ui.customComponents.menu.actions.template;

import com.barattoManager.old.exception.NoNodeSelected;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.old.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.TreeNode;

/**
 * Abstract class used to create a {@link MenuAction} that represent a node action template
 */
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
