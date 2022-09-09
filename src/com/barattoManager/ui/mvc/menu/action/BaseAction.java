package com.barattoManager.ui.mvc.menu.action;

import com.barattoManager.services.user.User;
import com.barattoManager.ui.mvc.tree.TreeController;

import javax.swing.tree.TreeNode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Abstract class that represent a base action
 */
public abstract class BaseAction {

	private final User user;
	private final TreeController<?> treeController;

	/**
	 * Constructor of the class
	 *
	 * @param user           {@link User} who has to do the action, who has logged in
	 * @param treeController {@link TreeController}
	 */
	public BaseAction(User user, TreeController<?> treeController) {
		this.user = user;
		this.treeController = treeController;
	}

	/**
	 * Method used to get a {@link TreeNode} array
	 *
	 * @return {@link TreeNode} array
	 */
	public TreeNode[] getNodePath() {

		return treeController.getModel().getTreeNodes();
	}

	/**
	 * Method used to get a UUID as a {@link String} from a {@link TreeNode} array
	 *
	 * @param treeNodes {@link TreeNode} array
	 * @return UUID as a {@link String}
	 */
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


	protected abstract void run();

	/**
	 * Method used to get a {@link User}
	 *
	 * @return {@link User}
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Method used to get a {@link TreeController}
	 *
	 * @return {@link TreeController}
	 */
	public TreeController<?> getTreeController() {
		return treeController;
	}
}
