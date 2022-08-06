package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.CategoryUpdateDataEventFactory;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

public class CategoryTreeController implements BaseController, ModelDataHasChangeListener {

	private final CategoryTreeModel model;
	private final CategoryTreeView view;

	public CategoryTreeController(CategoryTreeModel model, CategoryTreeView view) {
		this.model = model;
		this.view = view;

		this.model.addModelDataHasChangeListener(this);
		CategoryUpdateDataEventFactory.getEventHandler().addListener(model);

		this.view.drawTree(model.getCategories());
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
}
