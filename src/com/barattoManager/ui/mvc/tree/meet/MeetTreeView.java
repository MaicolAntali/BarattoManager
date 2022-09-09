package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.tree.TreeView;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.List;

public class MeetTreeView extends TreeView<Meet> {

	private static final String DAY_TO_ACCEPT_THE_OFFER = "Giorni per accettare l'offerta: %d";
	private static final String DAYS = "Giorno: %s";
	private static final String TIME = "Orario: %s-%s";

	@Override
	protected void drawNodes(List<Meet> list) {

		var nodeMap = new HashMap<String, DefaultMutableTreeNode>();

		list.forEach(
				meet -> {
					if (!nodeMap.containsKey(meet.getCity().trim().toLowerCase()))
						nodeMap.put(
								meet.getCity().trim().toLowerCase(),
								new DefaultMutableTreeNode(meet.getCity().trim())
						);

					createMeetNode(meet, nodeMap.get(meet.getCity().trim().toLowerCase()));
				}
		);

		nodeMap.values()
				.forEach(node -> getRootNode().add(node));
	}

	@Override
	protected String getRootNodeName() {
		return "Incontri";
	}

	private void createMeetNode(Meet meet, DefaultMutableTreeNode fatherNode) {
		var meetNode = new DefaultMutableTreeNode(meet.getSquare());

		meetNode.add(new DefaultMutableTreeNode(DAYS.formatted(meet.getDay().toString())));
		meetNode.add(new DefaultMutableTreeNode(TIME.formatted(meet.getStartTime(), meet.getEndTime())));
		meetNode.add(new DefaultMutableTreeNode(DAY_TO_ACCEPT_THE_OFFER.formatted(meet.getDaysBeforeExpire())));

		fatherNode.add(meetNode);
	}
}
