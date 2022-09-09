package com.barattoManager.ui.mvc.tree;

import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Generic abstract class that represent a tree controller
 *
 * @param <T> The type of {@link TreeModel} and {@link TreeView}
 */
public abstract class TreeController<T> extends GraspController implements ModelDataHasChangeListener {

	private final TreeModel<T> model;
	private final TreeView<T> view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link TreeModel}
	 * @param view  {@link TreeView}
	 */
	protected TreeController(TreeModel<T> model, TreeView<T> view) {
		this.model = model;
		this.view = view;

		initAction();

		this.view.addActionNotifierListener(this);
		this.view.drawTree(model.getData());
	}


	@Override
	public TreeModel<T> getModel() {
		return model;
	}

	@Override
	public TreeView<T> getView() {
		return view;
	}

	@Override
	public void dataChange() {
		getView().drawTree(getModel().getData());
	}

	@Override
	protected void initAction() {
		addAction("nodeSelectedChange", () -> {
			Object lastSelectedPathComponent = view.getTree().getLastSelectedPathComponent();

			if (lastSelectedPathComponent != null)
				model.setTreeNodes(((DefaultMutableTreeNode) lastSelectedPathComponent).getPath());
		});
	}
}
