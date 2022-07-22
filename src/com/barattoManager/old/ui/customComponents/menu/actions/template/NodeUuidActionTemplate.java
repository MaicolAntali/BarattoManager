package com.barattoManager.old.ui.customComponents.menu.actions.template;

import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.tree.Tree;

import javax.swing.tree.TreeNode;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Abstract class used to create a {@link NodeActionTemplate} that represent a node uuid action template
 */
public abstract class NodeUuidActionTemplate extends NodeActionTemplate {

	@Override
	protected void customAction(TreeNode[] nodePath, Tree tree, User user) {
		AtomicReference<String> uuid = new AtomicReference<>("");
		IntStream.range(0, nodePath[nodePath.length - 1].getChildCount())
				.forEach(i -> {
					if (nodePath[nodePath.length - 1].getChildAt(i).toString().startsWith("UUID:")) {
						uuid.set(nodePath[nodePath.length - 1].getChildAt(i).toString().split(":")[1].trim());
					}
				});

		customAction(uuid.get(), tree, user);
	}

	protected abstract void customAction(String uuid, Tree tree, User user);
}
