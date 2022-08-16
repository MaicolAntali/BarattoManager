package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;

import java.util.Objects;
import java.util.Optional;

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

	public static Optional<Category> getCategoryFromCategoryPath(String categoryPath) {
		var splitCategories = categoryPath.split("-");

		Optional<Category> optionalCategory = Optional.empty();
		for (int i = 0; i < splitCategories.length; i++) {
			if (i == 0) {
				optionalCategory = CategoryManagerFactory.getManager().getRootCategoryMap().values().stream()
						.filter(category -> Objects.equals(category.getName(), splitCategories[0].trim()))
						.findFirst();
			}
			else {
				final int finalI = i;
				optionalCategory = optionalCategory.orElseThrow(NullPointerException::new).getSubCategory().values().stream()
						.filter(category -> Objects.equals(category.getName(), splitCategories[finalI].trim()))
						.findFirst();
			}
		}

		return optionalCategory;
	}
}
