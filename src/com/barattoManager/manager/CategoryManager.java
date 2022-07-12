package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.model.category.Category;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static com.barattoManager.manager.Constants.*;

public final class CategoryManager implements Manager {

	private final ConcurrentHashMap<String, Category> categoryMap;

	public CategoryManager(ConcurrentHashMap<String, Category> categoryMap) {
		this.categoryMap = categoryMap;
	}

	/**
	 * Method used to add new Main category
	 *
	 * @param categoryName        Name of new category
	 * @param categoryDescription Description of the new category
	 * @throws AlreadyExistException  Is thrown if the category or field that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the name or the description is an empty string
	 * @throws NullCategoryException  Is thrown if is not found a category in the map
	 */
	public void addNewMainCategory(String categoryName, String categoryDescription) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		if (categoryName.trim().isBlank() || categoryDescription.isBlank())
			throw new IllegalValuesException(ERROR_CATEGORY_PARAMS_NOT_VALID);

		var newMainCategory = new Category(categoryName, categoryDescription);

		if (!isUniqueCategory(this.categoryMap.values().stream().toList(), newMainCategory.hashCode()))
			throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);

		this.categoryMap.put(newMainCategory.getUuid(), newMainCategory);

		for (JsonNode objNode : AppConfigurator.getInstance().getDefaultField()) {
			addNewField(
					new ArrayList<>(List.of("root", categoryName)),
					objNode.get("name").asText(),
					objNode.get("required").asBoolean()
			);
		}

		EventFactory.getCategoriesEvent().fireListener(this.categoryMap);
		assert this.categoryMap.containsKey(String.valueOf(newMainCategory.getUuid())) : POST_CONDITION_CATEGORY_NOT_IN_MAP;
	}

	/**
	 * Method used to add a new subcategory in the category tree.
	 *
	 * @param pathOfSubcategory      {@link ArrayList} that represent the path of the category
	 * @param subCategoryName        Name of the new category
	 * @param subCategoryDescription Description of new category
	 * @throws AlreadyExistException  Is thrown if the category that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the name is an empty string
	 * @throws NullCategoryException  Is thrown if is not found a category in the map
	 */
	public void addNewSubCategory(ArrayList<String> pathOfSubcategory, String subCategoryName, String subCategoryDescription) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		if (subCategoryName.trim().isBlank() || subCategoryDescription.isBlank())
			throw new IllegalValuesException(ERROR_INVALID_NAME_OF_SUBCATEGORY);

		Optional<Category> fatherCategory = getCategoryFromPath(pathOfSubcategory);
		var                newSubCategory = new Category(subCategoryName.trim(), subCategoryDescription);

		if (fatherCategory.isEmpty())
			throw new NullCategoryException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);

		if (!isUniqueCategory(this.categoryMap.values().stream().toList(), newSubCategory.hashCode()))
			throw new AlreadyExistException(ERROR_CATEGORY_ALREADY_EXISTS);

		fatherCategory.get().addSubCategory(newSubCategory);
		EventFactory.getCategoriesEvent().fireListener(this.categoryMap);

		assert fatherCategory.get().getSubCategory().containsKey(String.valueOf(newSubCategory.getUuid())) : POST_CONDITION_SUBCATEGORY_NOT_IN_MAP;
	}

	/**
	 * Recursive method used to add a new field in a category (and every sub category).
	 *
	 * @param pathOfCategory {@link ArrayList} that represent the path of the category
	 * @param fieldName      Name of the new field
	 * @param isRequired     If the field is required ({@code true}) otherwise {@code false}
	 * @throws AlreadyExistException  Is thrown if the new field that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the name is an empty string
	 * @throws NullCategoryException  Is thrown if is not found a category in the map
	 */
	public void addNewField(ArrayList<String> pathOfCategory, String fieldName, boolean isRequired) throws AlreadyExistException, IllegalValuesException, NullCategoryException {
		if (fieldName.trim().isBlank())
			throw new IllegalValuesException(ERROR_INVALID_NAME_OF_SUBCATEGORY);

		Optional<Category> category = getCategoryFromPath(pathOfCategory);

		if (category.isEmpty())
			throw new NullCategoryException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);

		if (!category.get().getSubCategory().isEmpty()) {
			if (category.get().getCategoryFields().containsKey(fieldName.trim().toLowerCase()))
				throw new AlreadyExistException(ERROR_FIELD_ALREADY_EXISTS);

			category.get().addNewFields(fieldName, isRequired);

			for (Category subCategory : category.get().getSubCategory().values()) {
				var newPath = new ArrayList<>(pathOfCategory);
				newPath.add(subCategory.getName());

				addNewField(newPath, fieldName, isRequired);
			}

			assert category.get().getCategoryFields().containsKey(fieldName.trim().toLowerCase()) : POST_CONDITION_FIELD_NOT_IN_MAP;
		}
		else {
			if (category.get().getCategoryFields().containsKey(fieldName.trim().toLowerCase()))
				throw new AlreadyExistException(ERROR_FIELD_ALREADY_EXISTS);

			category.get().addNewFields(fieldName, isRequired);
		}

		EventFactory.getCategoriesEvent().fireListener(this.categoryMap);
	}

	public ConcurrentHashMap<String, Category> getRootCategoryMap() {
		return this.categoryMap;
	}

	public Optional<Category> getCategoryByUuid(String uuid) {
		return getCategory(this.categoryMap.values().stream().toList(), uuid);
	}

	private Optional<Category> getCategory(List<Category> categories, String uuid) {
		AtomicReference<Optional<Category>> optionalAtomicReference = new AtomicReference<>(Optional.empty());

		if (!categories.isEmpty()) {
			categories.forEach(category -> {
				if (Objects.equals(category.getUuid(), uuid)) {
					optionalAtomicReference.set(Optional.of(category));
				}
				else {
					if (optionalAtomicReference.get().isEmpty()) {
						optionalAtomicReference.set(getCategory(category.getSubCategory().values().stream().toList(), uuid));
					}
				}
			});
		}

		return optionalAtomicReference.get();
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
				category = this.categoryMap.values().stream()
						.filter(cat -> Objects.equals(cat.getName(), pathOfCategory.get(finalI)))
						.findFirst();
			}
			else {
				if (category.isPresent())
					category = category.get().getSubCategory().values().stream()
							.filter(cat -> Objects.equals(cat.getName(), pathOfCategory.get(finalI)))
							.findFirst();
				else
					return Optional.empty();
			}
		}

		return category;
	}

	private boolean isUniqueCategory(List<Category> categories, int hashToCheck) {
		if (!categories.isEmpty()) {
			for (Category category : categories) {
				if (category.hashCode() != hashToCheck) {
					return isUniqueCategory(category.getSubCategory().values().stream().toList(), hashToCheck);
				}
				else {
					return false;
				}
			}
		}

		return true;
	}
}
