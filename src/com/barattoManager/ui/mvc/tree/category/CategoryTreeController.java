package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.CategoryUpdateDataEventFactory;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerFor;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedistenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
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
	public BaseModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@Override
	public void dataChange() {
		this.view.drawTree(model.getCategories());
	}

	@TreeNodeSelectedListenerFor(sourceField = "tree")
	private void nodeSelectedChange() {
		model.setTreeNodes(((DefaultMutableTreeNode) view.getTree().getLastSelectedPathComponent()).getPath());
	}

}
