package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;

public class SelectCategoryController implements BaseController {

	private final SelectCategoryModel model;
	private final SelectCategoryView view;

	public SelectCategoryController(SelectCategoryModel model, SelectCategoryView view) {
		this.model = model;
		this.view = view;

		this.view.draw(this.model.getCategoriesName());
	}

	@Override
	public BaseModel getModel() {
		return model;
	}

	@Override
	public SelectCategoryView getView() {
		return view;
	}
}
