package com.barattoManager.ui.mvc.tree;

import com.barattoManager.services.event.UpdateDataListener;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public abstract class TreeModel<T> implements BaseModel, UpdateDataListener<String, T> {

	private final ArrayList<ModelDataHasChangeListener> listeners;
	private List<T> data;
	private TreeNode[] treeNodes;

	protected TreeModel(List<T> data) {
		listeners = new ArrayList<>();

		this.data = data;
		this.treeNodes = null;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
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

	protected void fireModelDataHasChangeListener() {
		this.listeners.forEach(ModelDataHasChangeListener::dataChange);
	}
}
