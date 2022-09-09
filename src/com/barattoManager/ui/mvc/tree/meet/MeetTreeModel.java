package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.tree.TreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model of {@link MeetTreeController} that contains the data
 */
public class MeetTreeModel extends TreeModel<Meet> {

	/**
	 * Constructor of the class
	 * @param meets {@link List}
	 */
	public MeetTreeModel(List<Meet> meets) {
		super(meets);
	}

	@Override
	public void update(ConcurrentHashMap<String, Meet> updatedMap) {

		setData(updatedMap.values().stream()
				.filter(Meet::isMeetNotInPast)
				.toList());

		fireModelDataHasChangeListener();
	}
}
