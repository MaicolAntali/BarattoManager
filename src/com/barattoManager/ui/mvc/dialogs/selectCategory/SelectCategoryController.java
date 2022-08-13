package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;

public class SelectCategoryController implements BaseController {

	private final SelectCategoryModel model;
	private final SelectCategoryView view;

	public SelectCategoryController(SelectCategoryModel model, SelectCategoryView view) {
		this.model = model;
		this.view = view;

		this.view.draw(this.model.getCategoriesName());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public SelectCategoryModel getModel() {
		return model;
	}

	@Override
	public SelectCategoryView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "comboBox")
	private void comboBoxValueHasChanged() {
		model.setCategoryNamesSelected(view.getSelectedCategoryName());
	}
}
