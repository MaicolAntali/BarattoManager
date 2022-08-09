package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.event.UpdateDataListener;
import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MeetTreeModel implements BaseModel, UpdateDataListener<String, Meet> {

	private final ArrayList<ModelDataHasChangeListener> listeners;
	private List<Meet> meets;
	private TreeNode[] treeNodes;

	public MeetTreeModel(List<Meet> meets) {
		this.meets = meets.stream().filter(Meet::isMeetNotInPast).toList();

		listeners = new ArrayList<>();
		treeNodes = null;
	}

	@Override
	public void update(ConcurrentHashMap<String, Meet> updatedMap) {
		meets = updatedMap.values().stream()
				.filter(Meet::isMeetNotInPast)
				.toList();
		fireModelDataHasChangeListener();
	}


	public List<Meet> getCategories() {
		return meets;
	}

	public TreeNode[] getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(TreeNode[] treeNodes) {
		this.treeNodes = treeNodes;
	}

	public void addModelDataHasChangeListener(ModelDataHasChangeListener listener) {
		this.listeners.add(listener);
	}

	private void fireModelDataHasChangeListener() {
		this.listeners.forEach(ModelDataHasChangeListener::dataChange);
	}
}
