package com.barattoManager.category;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.CategoryAlreadyExist;
import com.barattoManager.exception.FieldAlreadyExist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the categories.
 */
public final class CategoryManager {
	private final File categoriesFile = new File(AppConfigurator.getInstance().getFileName("category_file"));
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final HashMap<String, Category> categoryMap;

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

	private static final class CategoryManagerHolder {
		private static final CategoryManager instance = new CategoryManager();
	}

	/**
	 * Method used to crete get the {@link CategoryManager} instance.
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
	 * Method used to add a new category in the category tree.
	 *
	 * @param pathOfSubcategory {@link ArrayList} that represent the path of the category
	 * @param name              Name of the new category
	 * @param description       Description of new category
	 * @return {@link Category} the new category object
	 * @throws CategoryAlreadyExist Is thrown is the category that are trying to add already exist.
	 */
	public Category addNewSubCategory(ArrayList<String> pathOfSubcategory, String name, String description) throws CategoryAlreadyExist {
		Category category = getCategoryFromPath(pathOfSubcategory);

		assert category != null;
		if (!category.getSubCategory().containsKey(name.toLowerCase())) {
			category.addSubCategory(name, description);
			saveCategoryMapChange();
			return category.getSubCategory().get(name.toLowerCase());
		}
		else {
			throw new CategoryAlreadyExist("La categoria che stai creando esiste gia.");
		}

	}

	/**
	 * Method used to add a new filed in a category (and every sub category).
	 *
	 * @param pathOfCategory {@link ArrayList} that represent the path of the category
	 * @param name           Name of the new field
	 * @param isRequired     If the field is required ({@code true}) otherwise {@code false}
	 * @return {@link Category} object with the new field
	 * @throws FieldAlreadyExist Is thrown if the new field that are trying to add already exist.
	 */
	public Category addNewField(ArrayList<String> pathOfCategory, String name, boolean isRequired) throws FieldAlreadyExist {
		Category category = getCategoryFromPath(pathOfCategory);

		if (!category.getCategoryFields().containsKey(name.toLowerCase())) {
			category.addNewFields(name, isRequired);
			for (Category cat : category.getSubCategory().values()) {
				cat.addNewFields(name, isRequired);
			}
			saveCategoryMapChange();
			return category;
		}
		else {
			throw new FieldAlreadyExist("La categoria che stai creato esiste già.");
		}
	}

	/**
	 * Method used to get a {@link Category} object for a categoryPath ({@code ArrayList<String>})
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
