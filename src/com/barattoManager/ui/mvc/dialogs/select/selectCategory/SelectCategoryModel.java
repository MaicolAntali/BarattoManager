package com.barattoManager.ui.mvc.dialogs.select.selectCategory;

import com.barattoManager.services.category.Category;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.dialogs.select.selectArticle.SelectArticleController;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of {@link SelectArticleController} that contains the data
 */
public class SelectCategoryModel implements Model {

	private final List<String> categoriesName;
	private String categoryNamesSelected = "";

	/**
	 * Constructor of the class
	 * @param categories {@link List} of categories
	 */
	public SelectCategoryModel(List<Category> categories) {
		this.categoriesName = generateCategoryName(categories);
	}

	/**
	 * Method used to get {@link List} of categories
	 * @return {@link List} of categories
	 */
	public List<String> getCategoriesName() {
		return categoriesName;
	}

	/**
	 * Method used to get the selected category name
	 * @return {@link String} of the selected category name
	 */
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
