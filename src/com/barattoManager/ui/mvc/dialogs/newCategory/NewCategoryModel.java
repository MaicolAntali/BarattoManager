package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.mvc.base.BaseModel;

public class NewCategoryModel implements BaseModel {

	private String categoryName;
	private String categoryDescription;

	public NewCategoryModel() {
		this.categoryName = "";
		this.categoryDescription = "";
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
}
