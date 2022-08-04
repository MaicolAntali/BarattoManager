package com.barattoManager.services.category;

import com.barattoManager.services.category.field.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Category {
	private static final String PRE_CONDITION_SUBCATEGORY_ALREADY_EXIST = "Pre-condition: Subcategory already exist in the hashmap.";
	private static final String PRE_CONDITION_CATEGORY_NAME_IS_BLANK = "Pre-condition: Category name is blank";
	private static final String PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK = "Pre-condition: Category description is blank";
	private static final String POST_CONDITION_HASHMAP_SUBCATEGORY_IS_NOT_INITIALIZE = "Post-condition: The HashMap of sub-category is not initialize";
	private static final String POST_CONDITION_HASHMAP_FIELDS_IS_NOT_INITIALIZE = "Post-condition: The HashMap of fields is not initialize";
	private static final String PRE_CONDITION_FIELD_NAME_IS_BLANK = "Pre-condition: Field name is blank";
	private static final String PRE_CONDITION_FIELD_ALREADY_EXIST = "Pre-condition: Field already exist";
	private static final String POST_CONDITION_FIELDS_NOT_ADDED_HASHMAP = "Post-condition: Fields has not been added to the hashmap";

	@JsonProperty("uuid")
	private final String uuid;
	@JsonProperty("name")
	private final String name;
	@JsonProperty("description")
	private final String description;
	@JsonProperty("sub_categories")
	private final HashMap<String, Category> subCategory;
	@JsonProperty("fields")
	private final HashMap<String, Field> categoryFields;


	/**
	 * Constructor of the class
	 *
	 * @param name        Name of the category
	 * @param description Description of the category
	 */
	public Category(String name, String description) {
		assert !name.isBlank() : PRE_CONDITION_CATEGORY_NAME_IS_BLANK;
		assert !description.isBlank() : PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK;

		this.uuid = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
		this.subCategory = new HashMap<>();
		this.categoryFields = new HashMap<>();
	}

	/**
	 * Constructor of the class
	 *
	 * @param uuid           Uuid of the category
	 * @param name           Name of the category
	 * @param description    Description of the category
	 * @param subCategory    {@link HashMap} that contains each sub-category
	 * @param categoryFields {@link HashMap} that contains the fields of teh category
	 */
	public Category(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("sub_categories") HashMap<String, Category> subCategory,
			@JsonProperty("fields") HashMap<String, Field> categoryFields
	) {
		assert !name.isBlank() : PRE_CONDITION_CATEGORY_NAME_IS_BLANK;
		assert !description.isBlank() : PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK;

		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.subCategory = subCategory;
		this.categoryFields = categoryFields;

		assert subCategory != null : POST_CONDITION_HASHMAP_SUBCATEGORY_IS_NOT_INITIALIZE;
		assert categoryFields != null : POST_CONDITION_HASHMAP_FIELDS_IS_NOT_INITIALIZE;
	}

	/**
	 * Method used to add a new sub-category
	 *
	 * @param category {@link Category} object to add as a sub-category
	 */
	public void addSubCategory(Category category) {
		assert !subCategory.containsKey(category.getName().toLowerCase()) : PRE_CONDITION_SUBCATEGORY_ALREADY_EXIST;

		category.addNewFields(categoryFields);
		subCategory.put(category.getUuid(), category);
	}

	/**
	 * Method used to add a new {@link Field fields}
	 *
	 * @param newFields {@link HashMap} that contains the fields to add
	 */
	public void addNewFields(HashMap<String, Field> newFields) {
		categoryFields.putAll(newFields);
	}

	/**
	 * Method used to add a new {@link Field} to this category
	 *
	 * @param name     Name of the field
	 * @param required Is true the field is required otherwise no
	 */
	public void addNewFields(String name, boolean required) {
		assert !name.isBlank() : PRE_CONDITION_FIELD_NAME_IS_BLANK;
		assert !categoryFields.containsKey(name.toLowerCase()) : PRE_CONDITION_FIELD_ALREADY_EXIST;

		categoryFields.put(name.toLowerCase(), new Field(name, required));

		assert categoryFields.containsKey(name.toLowerCase()) : POST_CONDITION_FIELDS_NOT_ADDED_HASHMAP;
	}

	/**
	 * Method used to get the uuid of the category
	 *
	 * @return Uuid of the category
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the name of category
	 *
	 * @return Name of the category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to get the description of category
	 *
	 * @return Description of the category
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method used to return an {@link HashMap} that contains all sub-categories
	 *
	 * @return {@link HashMap} that contains all sub-categories
	 */
	public HashMap<String, Category> getSubCategory() {
		return subCategory;
	}

	/**
	 * Method used to get the  {@link HashMap} that contains all fields
	 *
	 * @return {@link HashMap} that contains all fields
	 */
	public HashMap<String, Field> getCategoryFields() {
		return categoryFields;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName().toLowerCase());
	}
}
