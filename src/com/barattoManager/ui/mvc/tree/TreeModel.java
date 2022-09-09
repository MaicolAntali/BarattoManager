package com.barattoManager.ui.mvc.tree;

import com.barattoManager.services.event.UpdateDataListener;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic abstract class that represent a tree model
 *
 * @param <T> The type of data structure
 */
public abstract class TreeModel<T> implements Model, UpdateDataListener<String, T> {

	private final ArrayList<ModelDataHasChangeListener> listeners;
	private List<T> data;
	private TreeNode[] treeNodes;

	/**
	 * Constructor of the class
	 *
	 * @param data {@link List}
	 */
	protected TreeModel(List<T> data) {
		listeners = new ArrayList<>();

		this.data = data;
		this.treeNodes = null;
	}

	/**
	 * Method used to get data
	 *
	 * @return {@link List} of data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * Method used to set data
	 *
	 * @param data {@link List} to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * Method used to get tree nodes
	 *
	 * @return tree {@link TreeNode}
	 */
	public TreeNode[] getTreeNodes() {
		return treeNodes;
	}

	/**
	 * Method used to set tree nodes
	 *
	 * @param treeNodes {@link TreeNode} to set
	 */
	public void setTreeNodes(TreeNode[] treeNodes) {
		this.treeNodes = treeNodes;
	}

	/**
	 * Method used to add a {@link  ModelDataHasChangeListener}
	 *
	 * @param listener {@link ModelDataHasChangeListener}
	 */
	public void addModelDataHasChangeListener(ModelDataHasChangeListener listener) {
		this.listeners.add(listener);
	}

	protected void fireModelDataHasChangeListener() {
		this.listeners.forEach(ModelDataHasChangeListener::dataChange);
	}
}
