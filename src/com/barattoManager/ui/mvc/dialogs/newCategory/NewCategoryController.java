package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class NewCategoryController implements BaseController {

	private final NewCategoryModel model;
	private final NewCategoryView view;

	public NewCategoryController(NewCategoryModel model, NewCategoryView view) {
		this.model = model;
		this.view = view;

		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewCategoryModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@DocumentListenerFor(sourceField = "categoryNameField")
	private void categoryNameHasChanged() {
		model.setCategoryName(view.getCategoryName());
	}

	@DocumentListenerFor(sourceField = "categoryDescriptionField")
	private void categoryDescriptionHasChanged() {
		model.setCategoryDescription(view.getCategoryDescription());
	}
}