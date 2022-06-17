package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.manager.MeetManager;
import com.barattoManager.model.meet.Meet;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class used to create a JPanel that contain a JTree
 */
public class MeetTree extends JPanel{
	/**
	 * Icon for open category
	 */
	private static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	/**
	 * Icon for close category
	 */
	private static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	/**
	 * Icon for field
	 */
	private static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";

	/**
	 * {@link MeetTree} Constructor
	 * @param dimension Dimension of the JPanel
	 */
	public MeetTree(Dimension dimension) {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Incontri");

		var nodeMap = new HashMap<String, DefaultMutableTreeNode>();

		// Create all meet
		for (Meet meet : MeetManager.getInstance().getMeets()) {
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
			rootNode.add(node);
		}

		var tree = new JTree(rootNode);

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));


		add(new JScrollPane(tree)).setPreferredSize(dimension);
		setVisible(true);
	}

	/**
	 * {@link MeetTree} Constructor
	 */
	public MeetTree() {
		this(new Dimension(500, 290));
	}

	/**
	 * Method used to create a new meet node
	 *
	 * @param meet {@link Meet} want to create the node.
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
