package com.barattoManager.ui.mvc.tree;

import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedInstaller;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerFor;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class TreeController<T> implements BaseController, ModelDataHasChangeListener {

	private final TreeModel<T> model;
	private final TreeView<T> view;

	protected TreeController(TreeModel<T> model, TreeView<T> view) {
		this.model = model;
		this.view = view;

		this.view.drawTree(model.getData());

		TreeNodeSelectedInstaller.processAnnotations(this, view);
	}

	@Override
	public TreeModel<T> getModel() {
		return model;
	}

	@Override
	public TreeView<T> getView() {
		return view;
	}

	@TreeNodeSelectedListenerFor(sourceField = "tree")
	private void nodeSelectedChange() {
		Object lastSelectedPathComponent = view.getTree().getLastSelectedPathComponent();

		if (lastSelectedPathComponent != null)
			model.setTreeNodes(((DefaultMutableTreeNode) lastSelectedPathComponent).getPath());
	}

	@Override
	public void dataChange() {
		getView().drawTree(getModel().getData());
		TreeNodeSelectedInstaller.processAnnotations(this, getView());
	}
}
