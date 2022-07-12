package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.model.meet.Meet;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Class used to create a JPanel that contains a JTree of meets
 */
public class MeetTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	public MeetTree(List<Meet> meets, Dimension dimension) {
		super(dimension);

		var nodeMap = new HashMap<String, DefaultMutableTreeNode>();

		// Create all meet
		for (Meet meet : meets) {
			if (nodeMap.containsKey(meet.getCity().trim().toLowerCase())) {
				createMeetNode(meet, nodeMap.get(meet.getCity().trim().toLowerCase()));
			}
			else {
				nodeMap.put(meet.getCity().trim().toLowerCase(), new DefaultMutableTreeNode(meet.getCity()));
				createMeetNode(meet, nodeMap.get(meet.getCity().trim().toLowerCase()));
			}
		}

		// add city to root node
		for (DefaultMutableTreeNode node : nodeMap.values()) {
			getRootNode().add(node);
		}

		getTree().expandPath(new TreePath(getRootNode()));
	}

	/**
	 * {@link MeetTree} Constructor
	 */
	public MeetTree(List<Meet> meets) {
		this(meets, new Dimension(500, 290));
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Incontri");

		return rootNode;
	}


	/**
	 * Method used to create a new meet node
	 *
	 * @param meet       {@link Meet} want to create the node.
	 * @param fatherNode {@link DefaultMutableTreeNode} node to attach the new meet node
	 */
	private void createMeetNode(Meet meet, DefaultMutableTreeNode fatherNode) {
		var meetNode = new DefaultMutableTreeNode(meet.getSquare());

		meetNode.add(new DefaultMutableTreeNode("Giorno: %s".formatted(meet.getDay().toString())));
		meetNode.add(new DefaultMutableTreeNode("Orario: %s-%s".formatted(meet.getStartTime(), meet.getEndTime())));
		meetNode.add(new DefaultMutableTreeNode("Giorni per accettare l'offerta: %d".formatted(meet.getDaysBeforeExpire())));

		// add meetNode to father
		fatherNode.add(meetNode);
	}
}
