package com.barattoManager.ui.mvc.menu.action;

import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.tree.TreeController;

import javax.swing.tree.TreeNode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public abstract class BaseAction {

	public TreeNode[] getNodePath(User user, TreeController<?> treeController) {

		return treeController.getModel().getTreeNodes();
	}

	public String getUUIDFromNodes(TreeNode[] treeNodes) {

		var stringAtomicReference = new AtomicReference<>("");

		IntStream.range(0, treeNodes[treeNodes.length - 1].getChildCount())
				.forEach(i -> {
					if (treeNodes[treeNodes.length - 1].getChildAt(i).toString().startsWith("UUID:"))
						stringAtomicReference.set(treeNodes[treeNodes.length - 1].getChildAt(i).toString().split(":")[1].trim());
				});

		return stringAtomicReference.get();
	}

	public abstract void run();
}