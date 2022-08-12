package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.tree.TreeModel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MeetTreeModel extends TreeModel<Meet> {


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
