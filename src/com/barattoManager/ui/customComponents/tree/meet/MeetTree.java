package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.meet.Meet;
import com.barattoManager.meet.MeetManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;

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

	public MeetTree() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Incontri");

		var nodeMap = new HashMap<String, DefaultMutableTreeNode>();

		// Create all meet
		for (Meet meet : MeetManager.getInstance().getMeetArrayList()) {
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


		add(new JScrollPane(tree)).setPreferredSize(new Dimension(500, 290));
		setVisible(true);

	}

	private void createMeetNode(Meet meet, DefaultMutableTreeNode fatherNode) {
		// create the square node
		var meetNode = new DefaultMutableTreeNode(meet.getSquare());

		// create days nodes
		var daysNode = new DefaultMutableTreeNode(meet.getDays().size() == 1 ? "Giorno" : "Giorni");
		for (String day : meet.getDays()) {
			var dayNode = new DefaultMutableTreeNode(day);
			daysNode.add(dayNode);
		}

		// create intervals nodes
		var intervalsNode = new DefaultMutableTreeNode("Orari");
		for (LocalTime time : meet.getIntervals()) {
			var timeNode = new DefaultMutableTreeNode("%02d:%02d".formatted(time.getHour(), time.getMinute()));
			intervalsNode.add(timeNode);
		}

		// add the days and intervals node to the meet node
		meetNode.add(daysNode);
		meetNode.add(intervalsNode);

		// add meetNode to father
		fatherNode.add(meetNode);
	}
}
