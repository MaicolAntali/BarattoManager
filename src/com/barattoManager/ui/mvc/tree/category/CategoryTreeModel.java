package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.event.UpdateDataListener;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryTreeModel implements BaseModel, UpdateDataListener<String, Category> {

	private final ArrayList<ModelDataHasChangeListener> listeners;
	private List<Category> categories;
	private TreeNode[] treeNodes;

	public CategoryTreeModel(List<Category> categories) {
		listeners = new ArrayList<>();
		this.categories = categories;
	}

	@Override
	public void update(ConcurrentHashMap<String, Category> updatedMap) {
		this.categories = updatedMap.values().stream().toList();
		fireModelDataHasChangeListener();
	}


	public List<Category> getCategories() {
		return categories;
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
