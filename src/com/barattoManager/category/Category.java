package com.barattoManager.category;

import com.barattoManager.category.field.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that represent a category
 */
public class Category {
	/**
	 * Pre-condition: Subcategory already exist in the hashmap.
	 */
	private static final String PRE_CONDITION_SUBCATEGORY_ALREADY_EXIST = "Pre-condition: Subcategory already exist in the hashmap.";
	/**
	 * Pre-condition: Category name is blank
	 */
	private static final String PRE_CONDITION_CATEGORY_NAME_IS_BLANK = "Pre-condition: Category name is blank";
	/**
	 * Pre-condition: Category description is blank
	 */
	private static final String PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK = "Pre-condition: Category description is blank";
	/**
	 * Post-condition: The HashMap of sub-category is not initialize
	 */
	private static final String POST_CONDITION_HASHMAP_SUBCATEGORY_IS_NOT_INITIALIZE = "Post-condition: The HashMap of sub-category is not initialize";
	/**
	 * Post-condition: The HashMap of fields is not initialize
	 */
	private static final String POST_CONDITION_HASHMAP_FIELDS_IS_NOT_INITIALIZE = "Post-condition: The HashMap of fields is not initialize";
	/**
	 * Pre-condition: Subcategory name is blank
	 */
	private static final String PRE_CONDITION_SUBCATEGORY_NAME_IS_BLANK = "Pre-condition: Subcategory name is blank";
	/**
	 * Pre-condition: Subcategory description is blank
	 */
	private static final String PRE_CONDITION_SUBCATEGORY_DESCRIPTION_IS_BLANK = "Pre-condition: Subcategory description is blank";
	/**
	 * Post-condition: Subcategory has not been added to the hashmap
	 */
	private static final String POST_CONDITION_SUBCATEGORY_NOT_ADDED_TO_HASHMAP = "Post-condition: Subcategory has not been added to the hashmap";
	/**
	 * Pre-condition: Field name is blank
	 */
	private static final String PRE_CONDITION_FIELD_NAME_IS_BLANK = "Pre-condition: Field name is blank";
	/**
	 * Pre-condition: Field already exist
	 */
	private static final String PRE_CONDITION_FIELD_ALREADY_EXIST = "Pre-condition: Field already exist";
	/**
	 * Post-condition: Fields has not been added to the hashmap
	 */
	private static final String POST_CONDITION_FIELDS_NOT_ADDED_HASHMAP = "Post-condition: Fields has not been added to the hashmap";
	/**
	 * Category uuid
	 */
	@JsonProperty("uuid")
	private final String uuid;
	/**
	 * Category name
	 */
	@JsonProperty("name")
	private final String name;
	/**
	 * Category description
	 */
	@JsonProperty("description")
	private final String description;
	/**
	 * {@link HashMap} of sub-category
	 */
	@JsonProperty("sub_categories")
	private final HashMap<String, Category> subCategory;
	/**
	 * {@link HashMap} of fields
	 */
	@JsonProperty("fields")
	private final HashMap<String, Field> categoryFields;

	/**
	 * {@link Category} constructor
	 * @param name Name of category
	 * @param description Description of category
	 * @param subCategory {@link HashMap} that contains each sub-category
	 * @param categoryFields {@link HashMap} that contains each fields
	 */
	public Category(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("sub_categories") HashMap<String, Category> subCategory,
			@JsonProperty("fields") HashMap<String, Field> categoryFields
	) {
			assert !name.isBlank() : PRE_CONDITION_CATEGORY_NAME_IS_BLANK;
			assert !description.isBlank(): PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK;

		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.subCategory = subCategory;
		this.categoryFields = categoryFields;

		assert subCategory != null : POST_CONDITION_HASHMAP_SUBCATEGORY_IS_NOT_INITIALIZE;
		assert categoryFields != null : POST_CONDITION_HASHMAP_FIELDS_IS_NOT_INITIALIZE;
	}

	/**
	 * {@link Category} constructor
	 * @param name Name of category
	 * @param description Description of category
	 */
	public Category(String name, String description) {
		assert !name.isBlank() : PRE_CONDITION_CATEGORY_NAME_IS_BLANK;
		assert !description.isBlank(): PRE_CONDITION_CATEGORY_DESCRIPTION_IS_BLANK;

		this.uuid = UUID.randomUUID().toString();
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
		assert !name.isBlank() : PRE_CONDITION_SUBCATEGORY_NAME_IS_BLANK;
		assert !description.isBlank() : PRE_CONDITION_SUBCATEGORY_DESCRIPTION_IS_BLANK;
		assert !subCategory.containsKey(name.toLowerCase()): PRE_CONDITION_SUBCATEGORY_ALREADY_EXIST;

		var newCategory = new Category(name, description);
		newCategory.addNewFields(categoryFields);
		subCategory.put(newCategory.getName().toLowerCase(), newCategory);

		assert subCategory.containsKey(name.toLowerCase()): POST_CONDITION_SUBCATEGORY_NOT_ADDED_TO_HASHMAP;
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
		assert !name.isBlank() : PRE_CONDITION_FIELD_NAME_IS_BLANK;
		assert !categoryFields.containsKey(name.toLowerCase()) : PRE_CONDITION_FIELD_ALREADY_EXIST;
				
		categoryFields.put(name.toLowerCase(), new Field(name, required));
		
		assert categoryFields.containsKey(name.toLowerCase()) : POST_CONDITION_FIELDS_NOT_ADDED_HASHMAP;
	}

	/**
	 * Method used to get the uuid of the category
	 * @return UUID of the category
	 */
	public String getUuid() {
		return uuid;
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

	/**
	 * Method used to hash the name of category ({@link #name})
	 * @return The hashcode of {@link #name}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName().toLowerCase());
	}
}
