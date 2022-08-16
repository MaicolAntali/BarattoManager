package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.ui.mvc.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryModel implements BaseModel {

	private final List<String> categoriesName;
	private String categoryNamesSelected = "";

	public SelectCategoryModel(List<Category> categories) {
		this.categoriesName = generateCategoryName(categories);
	}

	public List<String> getCategoriesName() {
		return categoriesName;
	}

	public String getCategoryNamesSelected() {
		return categoryNamesSelected;
	}

	public void setCategoryNamesSelected(String categoryNamesSelected) {
		this.categoryNamesSelected = categoryNamesSelected;
	}

	private ArrayList<String> generateCategoryName(List<Category> categories) {
		var arrayList = new ArrayList<String>();

		categories.forEach(category -> arrayList.addAll(
				generateCategoryName(
						category.getSubCategory().values().stream().toList(),
						category.getName()
				)
		));

		return arrayList;
	}

	private ArrayList<String> generateCategoryName(List<Category> categories, String string) {
		var arrayList = new ArrayList<String>();

		categories.forEach(category -> {
			if (category.getSubCategory().isEmpty()) {
				arrayList.add(string + " - %s".formatted(category.getName()));
			}
			else {
				arrayList.addAll(
						generateCategoryName(
								category.getSubCategory().values().stream().toList(),
								string + " - %s".formatted(category.getName())
						)
				);
			}
		});

		return arrayList;
	}
}