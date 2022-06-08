package com.barattoManager.category;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NullCategoryException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the categories.
 */
public final class CategoryManager {
	/**
	 * Post-condition: The category is not present in the map.
	 */
	public static final String POST_CONDITION_CATEGORY_NOT_IN_MAP = "Post-condition: The category is not present in the map.";
	/**
	 * Post-condition: The sub-category is not present in the map.
	 */
	public static final String POST_CONDITION_SUBCATEGORY_NOT_IN_MAP = "Post-condition: The sub-category is not present in the map.";
	/**
	 * Post-condition: The field is not present in the map.
	 */
	public static final String POST_CONDITION_FIELD_NOT_IN_MAP = "Post-condition: The field is not present in the map.";

	/**
	 * Category params not valid error
	 */
	private static final String ERROR_CATEGORY_PARAMS_NOT_VALID = "Il nome o la descrizione della categoria non è valido";
	/**
	 * Category already exists error
	 */
	private static final String ERROR_CATEGORY_ALREADY_EXISTS = "La categoria che stai creando esiste già.";
	/**
	 * No category has been selected error
	 */
	private static final String ERROR_NO_CATEGORY_HAS_BEEN_SELECTED = "Non è stata selezionata una categoria.";
	/**
	 * Invalid name of sub-category error
	 */
	private static final String ERROR_INVALID_NAME_OF_SUBCATEGORY = "Il nome della sotto-categoria non è valido";
	/**
	 * Category JSON file
	 */
	private final File categoriesFile = new File(AppConfigurator.getInstance().getFileName("category_file"));
	/**
	 * {@link ObjectMapper} object, used to parse JSON
	 */
	private final ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * {@link HashMap} that contain the root category (KEY: UUID, VALUE: Category obj)
	 */
	private final HashMap<String, Category> rootCategoryMap;

	/**
	 * {@link CategoryManager} constructor
	 */
	private CategoryManager() {
		if (categoriesFile.exists()) {
			try {
				rootCategoryMap = objectMapper.readValue(categoriesFile, new TypeReference<>() {});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			rootCategoryMap = new HashMap<>();
		}
	}

	/**
	 * Holder class of instance
	 */
	private static final class CategoryManagerHolder {
		/**
		 * Instance of {@link CategoryManager}
		 */
		private static final CategoryManager instance = new CategoryManager();
	}

	/**
	 * Method used to create get the {@link CategoryManager} instance.
	 * This method uses the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code #getInstance()} method is called.
	 * Also is thread safe cause every thread read the same {@link Category} instance.
	 *
	 * @return The Instance of {@link CategoryManager} class
	 */
	public static CategoryManager getInstance() {
		return CategoryManagerHolder.instance;
	}

	/**
	 * Method used to add new Main category
	 *
	 * @param name        Name of new category
	 * @param description Description of the new category
	 * @throws AlreadyExistException Is thrown if the category or field that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the name or the description is an empty string
	 * @throws NullCategoryException  Is thrown if is not found a category in the map
	 */
	public void addNewMainCategory(String name, String description) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		if (!name.trim().toLowerCase().isBlank() && !description.isBlank()) {
			// create the new category
			var category = new Category(name, description);

			// Check if the category already exist
			if (isUniqueCategory(rootCategoryMap, category.hashCode())) {

				// add in the map the new root category
				rootCategoryMap.put(category.getUuid(), category);

				// adding the default field
				for (final JsonNode objNode : AppConfigurator.getInstance().getDefaultField()) {
					addNewField(
							new ArrayList<>(List.of("root", name)),
							objNode.get("name").asText(),
							objNode.get("required").asBoolean()
					);
				}
				// Save map changes
				saveCategoryMapChange();

				assert rootCategoryMap.containsKey(name.trim().toLowerCase()) : POST_CONDITION_CATEGORY_NOT_IN_MAP;
			}
			else {
				throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
			}
		}
		else {
			throw new IllegalValuesException(ERROR_CATEGORY_PARAMS_NOT_VALID);
		}
	}

	/**
	 * Method used to add a new subcategory in the category tree.
	 *
	 * @param pathOfSubcategory {@link ArrayList} that represent the path of the category
	 * @param name              Name of the new category
	 * @param description       Description of new category
	 * @throws AlreadyExistException Is thrown if the category that are trying to add already exist.
	 * @throws IllegalValuesException  Is thrown if the name is an empty string
	 * @throws NullCategoryException Is thrown if is not found a category in the map
	 */
	public void addNewSubCategory(ArrayList<String> pathOfSubcategory, String name, String description) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		var categoryName = name.trim().toLowerCase();
		if (!categoryName.isBlank() && !description.isBlank()) {
			Optional<Category> fatherCategory = getCategoryFromPath(pathOfSubcategory);
			var newCategory = new Category(name, description);

			if (fatherCategory.isPresent()) {
				if (isUniqueCategory(rootCategoryMap, newCategory.hashCode())) {
					fatherCategory.get().addSubCategory(newCategory);
					saveCategoryMapChange();

					assert fatherCategory.get().getSubCategory().containsKey(categoryName): POST_CONDITION_SUBCATEGORY_NOT_IN_MAP;
				}
				else {
					throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
				}
			}
			else {
				throw new NullCategoryException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);
			}
		}
		else {
			throw new IllegalValuesException(ERROR_INVALID_NAME_OF_SUBCATEGORY);
		}
	}

	/**
	 * Recursive method used to add a new field in a category (and every sub category).
	 *
	 * @param pathOfCategory {@link ArrayList} that represent the path of the category
	 * @param name           Name of the new field
	 * @param isRequired     If the field is required ({@code true}) otherwise {@code false}
	 * @throws AlreadyExistException Is thrown if the new field that are trying to add already exist.
	 * @throws IllegalValuesException  Is thrown if the name is an empty string
	 * @throws NullCategoryException Is thrown if is not found a category in the map
	 */
	public void addNewField(ArrayList<String> pathOfCategory, String name, boolean isRequired) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		var fieldName = name.trim().toLowerCase();
		if (!fieldName.isBlank()) {
			Optional<Category> category = getCategoryFromPath(pathOfCategory);

			if (category.isPresent()) {
				// Recursive exit condition
				if (!category.get().getSubCategory().isEmpty()) {
					if (!category.get().getCategoryFields().containsKey(fieldName)) {
						// Add the field in the category
						category.get().addNewFields(name, isRequired);

						// Recursive block, for each sub-category re-run this method
						for (Category subCategory : category.get().getSubCategory().values()) {
							var newPath = new ArrayList<>(pathOfCategory);
							newPath.add(subCategory.getName());
							addNewField(newPath, name, isRequired);
						}

						assert category.get().getCategoryFields().containsKey(fieldName) : POST_CONDITION_FIELD_NOT_IN_MAP;
					}
					else {
						throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
					}
				}
				else {
					if (!category.get().getCategoryFields().containsKey(name.toLowerCase())) {
						category.get().addNewFields(name, isRequired);
					}
					else {
						throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
					}
				}

				saveCategoryMapChange();
			}
			else {
				throw new NullCategoryException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);
			}
		}
		else {
			throw new IllegalValuesException(ERROR_INVALID_NAME_OF_SUBCATEGORY);
		}

	}


	/**
	 * Method used to get the {@link HashMap} of root categories
	 * @return {@link HashMap} of root categories
	 */
	public HashMap<String, Category> getRootCategoryMap() {
		return rootCategoryMap;
	}

	/**
	 * Method used to get a Category by UUID
	 * @param uuid UUID of the category to get
	 * @return {@link Optional} that contains the category if it's find otherwise empty
	 */
	public Optional<Category> getCategoryByUuid(String uuid) {
		return getCategory(rootCategoryMap, uuid);
	}


	private Optional<Category> getCategory(HashMap<String, Category> categoryHashMap, String uuid) {
		Optional<Category> categoryOptional = Optional.empty();

		if (!categoryHashMap.isEmpty()) {
			for (Category category : categoryHashMap.values()) {
				if (Objects.equals(category.getUuid(), uuid)) {
					categoryOptional = Optional.of(category);
					break;
				}
				else {
					if (categoryOptional.isEmpty()) {
						categoryOptional = getCategory(category.getSubCategory(), uuid);
					}

				}
			}
		}

		return categoryOptional;
	}

	/**
	 * Method used to get a {@link Category} object from a categoryPath ({@code ArrayList<String>})
	 *
	 * @param pathOfCategory {@link ArrayList} Category path.
	 * @return {@link Category} object
	 */
	private Optional<Category> getCategoryFromPath(ArrayList<String> pathOfCategory) {
		Optional<Category> category = Optional.empty();

		for (int i = 1; i < pathOfCategory.size(); i++) {
			final int finalI = i;
			if (i == 1) {
				category = rootCategoryMap.values().stream()
						.filter(cat -> Objects.equals(cat.getName(), pathOfCategory.get(finalI)))
						.findFirst();
			}
			else {
				category = category.get().getSubCategory().values().stream()
					.filter(cat -> Objects.equals(cat.getName(), pathOfCategory.get(finalI)))
					.findFirst();
			}
		}

		return category;
	}

	/**
	 * Method used to check if a category is unique in the entire category tree.<br/>
	 * This method takes advantage of hashcode (if the hashcode of two objects is equal then the two objects are equal)
	 * @param categoryHashMap Hashmap where to start searching
	 * @param hashToCheck Hashcode used to check the uniqueness,
	 * @return True if the hashcode is unique in the entire hashmap and also unique in every sub hashmaps otherwise False
	 * @see Category#hashCode()
	 */
	private boolean isUniqueCategory(HashMap<String, Category> categoryHashMap, int hashToCheck) {
		if (!categoryHashMap.isEmpty()) {
			for (Category category : categoryHashMap.values()) {
				if (category.hashCode() != hashToCheck) {
					return isUniqueCategory(category.getSubCategory(), hashToCheck);
				}
				else {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Method used to save in the json file the {@link #rootCategoryMap} object
	 */
	private void saveCategoryMapChange() {
		try {
			objectMapper.writeValue(categoriesFile, rootCategoryMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
