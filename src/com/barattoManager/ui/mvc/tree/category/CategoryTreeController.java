package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.CategoryUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

public class CategoryTreeController extends TreeController<Category> implements ModelDataHasChangeListener {

	public CategoryTreeController(CategoryTreeModel model, CategoryTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		CategoryUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
