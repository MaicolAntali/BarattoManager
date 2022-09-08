package com.barattoManager.ui.mvc.dialogs.select.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.mvc.GraspController;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;

import java.util.Objects;
import java.util.Optional;

public class SelectCategoryController extends GraspController {

	private final SelectCategoryModel model;
	private final SelectView<String> view;

	public SelectCategoryController(SelectCategoryModel model, SelectView<String> view) {
		this.model = model;
		this.view = view;

		initAction();

		this.view.addActionNotifierListener(this);
		this.view.draw(this.model.getCategoriesName());
	}

	@Override
	public SelectCategoryModel getModel() {
		return model;
	}

	@Override
	public SelectView<String> getView() {
		return view;
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
	@Override
	protected void initAction() {
		addAction("comboBoxChanged", () -> model.setCategoryNamesSelected(view.getSelectedObject()));
	}
}
