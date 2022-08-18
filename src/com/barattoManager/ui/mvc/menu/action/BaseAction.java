package com.barattoManager.ui.mvc.menu.action;

import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.tree.TreeController;

import javax.swing.tree.TreeNode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public abstract class BaseAction {

	private final User user;
	private final TreeController<?> treeController;

	public BaseAction(User user, TreeController<?> treeController) {
		this.user = user;
		this.treeController = treeController;
	}


	public TreeNode[] getNodePath(User user, TreeController<?> treeController) {

		return treeController.getModel().getTreeNodes();
	}

	public String getUUIDFromNodes(TreeNode[] treeNodes) {

		var stringAtomicReference = new AtomicReference<>("");

		if (treeNodes == null)
			return "";

		IntStream.range(0, treeNodes[treeNodes.length - 1].getChildCount())
				.forEach(i -> {
					if (treeNodes[treeNodes.length - 1].getChildAt(i).toString().startsWith("UUID:"))
						stringAtomicReference.set(treeNodes[treeNodes.length - 1].getChildAt(i).toString().split(":")[1].trim());
				});

		return stringAtomicReference.get();
	}

	public abstract void run();

	public User getUser() {
		return user;
	}

	public TreeController<?> getTreeController() {
		return treeController;
	}
}
