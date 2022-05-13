package com.barattoManager.category;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.exception.NullCategoryException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the categories.
 */
public final class CategoryManager {
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
	 * {@link HashMap} that contain the root category
	 */
	private final HashMap<String, Category> categoryMap;

	/**
	 * {@link CategoryManager} constructor
	 */
	private CategoryManager() {
		if (categoriesFile.exists()) {
			try {
				categoryMap = objectMapper.readValue(categoriesFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			categoryMap = new HashMap<>();
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
	 * Method used to add a new Main category
	 *
	 * @param name        Name of new category
	 * @param description Description of the new category
	 * @throws AlreadyExistException Is thrown is the category or field that are trying to add already exist.
	 * @throws EmptyStringException Is thrown if the name is an empty string
	 * @throws NullCategoryException  Is thrown if is not found a category in the map
	 */
	public void addNewMainCategory(String name, String description) throws AlreadyExistException, EmptyStringException, NullCategoryException {
		if (!name.isEmpty() && !(name.trim().length() == 0)) {
			if (!categoryMap.containsKey(name.trim().toLowerCase())) {
				categoryMap.put(name.toLowerCase(), new Category(name, description));

				// adding the default field
				for (final JsonNode objNode : AppConfigurator.getInstance().getDefaultField()) {
					addNewField(
							new ArrayList<>(List.of("root", name.toLowerCase())),
							objNode.get("name").asText(),
							objNode.get("required").asBoolean()
					);
				}

				saveCategoryMapChange();
				categoryMap.get(name.toLowerCase());
			}
			else {
				throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
			}
		}
		else {
			throw new EmptyStringException(ERROR_CATEGORY_PARAMS_NOT_VALID);
		}

	}

	/**
	 * Method used to add a new subcategory in the category tree.
	 *
	 * @param pathOfSubcategory {@link ArrayList} that represent the path of the category
	 * @param name              Name of the new category
	 * @param description       Description of new category
	 * @throws AlreadyExistException Is thrown if the category that are trying to add already exist.
	 * @throws EmptyStringException Is thrown if the name is an empty string
	 * @throws NullCategoryException Is thrown if is nt found a category in the map
	 */
	public void addNewSubCategory(ArrayList<String> pathOfSubcategory, String name, String description) throws AlreadyExistException, EmptyStringException, NullCategoryException {
		if (!name.isEmpty() && !(name.trim().length() == 0)) {
			Category category = getCategoryFromPath(pathOfSubcategory);

			if (category != null) {
				if (!category.getSubCategory().containsKey(name.trim().toLowerCase()) && !Objects.equals(category.getName().trim().toLowerCase(), name.trim().toLowerCase())) {
					category.addSubCategory(name, description);
					saveCategoryMapChange();
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
			throw new EmptyStringException(ERROR_INVALID_NAME_OF_SUBCATEGORY);
		}
	}

	/**
	 * Recursive method used to add a new field in a category (and every sub category).
	 *
	 * @param pathOfCategory {@link ArrayList} that represent the path of the category
	 * @param name           Name of the new field
	 * @param isRequired     If the field is required ({@code true}) otherwise {@code false}
	 * @throws AlreadyExistException Is thrown if the new field that are trying to add already exist.
	 * @throws EmptyStringException Is thrown if the name is an empty string
	 * @throws NullCategoryException Is thrown if is not found a category in the map
	 */
	public void addNewField(ArrayList<String> pathOfCategory, String name, boolean isRequired) throws AlreadyExistException, EmptyStringException, NullCategoryException {
		if (!name.isEmpty() && !(name.trim().length() == 0)) {
			Category category = getCategoryFromPath(pathOfCategory);

			if (category != null) {
				// Recursive exit condition
				if (!category.getSubCategory().isEmpty()) {
					if (!category.getCategoryFields().containsKey(name.trim().toLowerCase())) {
						// Add the field in the category
						category.addNewFields(name, isRequired);

						// Recursive block, for each sub-category re-run this method
						for (Category subCategory : category.getSubCategory().values()) {
							var newPath = new ArrayList<>(pathOfCategory);
							newPath.add(subCategory.getName());
							addNewField(newPath, name, isRequired);
						}
					}
					else {
						throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);
					}
				}
				else {
					if (!category.getCategoryFields().containsKey(name.toLowerCase())) {
						category.addNewFields(name, isRequired);
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
			throw new EmptyStringException(ERROR_INVALID_NAME_OF_SUBCATEGORY);
		}

	}

	/**
	 * Method used to get a {@link Category} object from a categoryPath ({@code ArrayList<String>})
	 *
	 * @param pathOfCategory {@link ArrayList} Category path.
	 * @return {@link Category} object
	 */
	private Category getCategoryFromPath(ArrayList<String> pathOfCategory) {
		Category mainCategory = null;

		for (int i = 1; i < pathOfCategory.size(); i++) {
			if (i == 1) {
				mainCategory = categoryMap.get(pathOfCategory.get(i).toLowerCase());
			}
			else {
				mainCategory = mainCategory.getSubCategory().get(pathOfCategory.get(i).toLowerCase());
			}
		}

		return mainCategory;
	}

	/**
	 * Method used to get the {@link HashMap} of root categories
	 * @return {@link HashMap} of root categories
	 */
	public HashMap<String, Category> getCategoryMap() {
		return categoryMap;
	}

	/**
	 * Method used to save in the json file the {@link #categoryMap} object
	 */
	private void saveCategoryMapChange() {
		try {
			objectMapper.writeValue(categoriesFile, categoryMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
