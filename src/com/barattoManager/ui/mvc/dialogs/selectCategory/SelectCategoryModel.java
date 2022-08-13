package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.ui.mvc.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryModel implements BaseModel {

	private final List<String> categoriesName;

	public SelectCategoryModel(List<Category> categories) {
		this.categoriesName = generateCategoryName(categories);
	}

	public List<String> getCategoriesName() {
		return categoriesName;
	}

	private ArrayList<String> generateCategoryName(List<Category> categories) {
		var arrayList = new ArrayList<String>();

		for (Category category : categories) {
			arrayList.addAll(
					generateComboBoxItems(
							category.getSubCategory().values().stream().toList(),
							category.getName()
					)
			);
		}

		return arrayList;
	}

	private ArrayList<String> generateComboBoxItems(List<Category> categories, String string) {
		var arrayList = new ArrayList<String>();

		for (Category category : categories) {
			if (category.getSubCategory().isEmpty()) {
				arrayList.add(string + " - %s".formatted(category.getName()));
			}
			else {
				arrayList.addAll(
						generateComboBoxItems(
								category.getSubCategory().values().stream().toList(),
								string + " - %s".formatted(category.getName())
						)
				);
			}
		}

		return arrayList;
	}
}
