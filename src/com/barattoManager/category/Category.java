package com.barattoManager.category;

import com.barattoManager.category.field.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class Category {

	@JsonProperty("category_name")
	private final String name;
	@JsonProperty("category_description")
	private final String description;
	@JsonProperty("category_sub_categories")
	private final HashMap<String, Category> subCategory;
	@JsonProperty("category_fields")
	private final HashMap<String, Field> categoryFields;

	public Category(
			@JsonProperty("category_name") String name,
			@JsonProperty("category_description") String description,
			@JsonProperty("category_sub_categories") HashMap<String, Category> subCategory,
			@JsonProperty("category_fields") HashMap<String, Field> categoryFields
	) {
		this.name = name;
		this.description = description;
		this.subCategory = subCategory;
		this.categoryFields = categoryFields;
	}

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
		this.subCategory = new HashMap<>();
		this.categoryFields = new HashMap<>();
	}

	public void addSubCategory(String name, String description) {
		var newCategory = new Category(name, description);
		newCategory.addNewFields(categoryFields);
		subCategory.put(newCategory.getName().toLowerCase(), newCategory);
	}

	public void addNewFields(HashMap<String, Field> newFields) {
		categoryFields.putAll(newFields);
	}

	public void addNewFields(String name, boolean required) {
		categoryFields.put(name.toLowerCase(), new Field(name, required));
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public HashMap<String, Category> getSubCategory() {
		return subCategory;
	}

	public HashMap<String, Field> getCategoryFields() {
		return categoryFields;
	}
}
