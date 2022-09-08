package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.login.LoginController;

/**
 * Model of {@link NewCategoryController} that contains the data
 */
public class NewCategoryModel implements Model {

	private String categoryName;
	private String categoryDescription;

	/**
	 * Constructor of the class
	 */
	public NewCategoryModel() {
		this.categoryName = "";
		this.categoryDescription = "";
	}

	/**
	 * Method used to get the name of category as a {@link String}
	 * @return Name of the category
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Method used to set a category name as a {@link String}
	 * @param categoryName {@link String} to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Method used to get the description of category as a {@link String}
	 * @return Description of the category
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * Method used to set the description of category as a {@link String}
	 * @param categoryDescription {@link String} to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
}
