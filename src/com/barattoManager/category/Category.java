package com.barattoManager.category;

import com.barattoManager.category.field.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * Class that represent a category
 */
public class Category {

	/**
	 * Category name
	 */
	@JsonProperty("category_name")
	private final String name;
	/**
	 * Category description
	 */
	@JsonProperty("category_description")
	private final String description;
	/**
	 * {@link HashMap} of sub-category
	 */
	@JsonProperty("category_sub_categories")
	private final HashMap<String, Category> subCategory;
	/**
	 * {@link HashMap} of fields
	 */
	@JsonProperty("category_fields")
	private final HashMap<String, Field> categoryFields;

	/**
	 * {@link Category} constructor
	 * @param name Name of category
	 * @param description Description of category
	 * @param subCategory {@link HashMap} that contains each sub-category
	 * @param categoryFields {@link HashMap} that contains each fields
	 */
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

	/**
	 * {@link Category} constructor
	 * @param name Name of category
	 * @param description Description of category
	 */
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
		this.subCategory = new HashMap<>();
		this.categoryFields = new HashMap<>();
	}

	/**
	 * Method used to add a new sub-category to this category
	 * @param name Name of sub-category
	 * @param description Description of sab-category
	 */
	public void addSubCategory(String name, String description) {
		var newCategory = new Category(name, description);
		newCategory.addNewFields(categoryFields);
		subCategory.put(newCategory.getName().toLowerCase(), newCategory);
	}

	/**
	 * Method used to add a new {@link Field}s to this category
	 * @param newFields {@link HashMap} that contains the fields to add
	 */
	public void addNewFields(HashMap<String, Field> newFields) {
		categoryFields.putAll(newFields);
	}

	/**
	 * Method used to add a new {@link Field} to this category
	 * @param name Name of field
	 * @param required Is true if the field is required otherwise no
	 */
	public void addNewFields(String name, boolean required) {
		categoryFields.put(name.toLowerCase(), new Field(name, required));
	}

	/**
	 * Method used to get the name of category
	 * @return Name of category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to get the description of category
	 * @return Description of category
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method used to get the HashMap of sub-categories
	 * @return {@link HashMap} of sub-categories
	 */
	public HashMap<String, Category> getSubCategory() {
		return subCategory;
	}

	/**
	 * Method used to get the HashMap of fields
	 * @return {@link HashMap} of fields
	 */
	public HashMap<String, Field> getCategoryFields() {
		return categoryFields;
	}
}
