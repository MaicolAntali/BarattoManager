package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.CategoryUpdateDataEventFactory;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerFor;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedistenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryTreeController implements BaseController, ModelDataHasChangeListener {

	private final CategoryTreeModel model;
	private final CategoryTreeView view;

	public CategoryTreeController(CategoryTreeModel model, CategoryTreeView view) {
		this.model = model;
		this.view = view;

		this.model.addModelDataHasChangeListener(this);
		CategoryUpdateDataEventFactory.getEventHandler().addListener(model);

		this.view.drawTree(model.getCategories());

		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}


	@Override
	public CategoryTreeModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@Override
	public void dataChange() {
		this.view.drawTree(model.getCategories());
		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}

	@TreeNodeSelectedListenerFor(sourceField = "tree")
	private void nodeSelectedChange() {
		Object lastSelectedPathComponent = view.getTree().getLastSelectedPathComponent();

		if (lastSelectedPathComponent != null)
			model.setTreeNodes(((DefaultMutableTreeNode) lastSelectedPathComponent).getPath());
	}

}
