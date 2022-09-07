package com.barattoManager.services.category;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class CategoryManager {

	private static final String ERROR_CATEGORY_PARAMS_NOT_VALID = "Il nome o la descrizione della categoria non è valido";
	private static final String ERROR_CATEGORY_ALREADY_EXISTS = "La categoria che stai creando esiste già.";
	private static final String POST_CONDITION_CATEGORY_NOT_IN_MAP = "Post-condition: The category is not present in the map.";
	private static final String ERROR_NO_CATEGORY_HAS_BEEN_SELECTED = "Non è stata selezionata una categoria.";
	private static final String POST_CONDITION_SUBCATEGORY_NOT_IN_MAP = "Post-condition: The sub-category is not present in the map.";
	private static final String ERROR_FIELD_ALREADY_EXISTS = "Il campo che stai creando esiste già.";
	private static final String POST_CONDITION_FIELD_NOT_IN_MAP = "Post-condition: The field is not present in the map.";
	private static final String ERROR_NAME_OR_DESCRIPTION_ARE_INVALID = "Il nome e/o la descrizione della sotto-categoria non sono validi";
	private static final String ERROR_SUB_CATEGORY_ALREADY_EXISTS = "La sotto-categoria che stai creando esiste già.";
	private static final String ERROR_INVALID_FIELD_NAME = "Il nome del campo non è valido";


	private final ConcurrentHashMap<String, Category> categoryMap;

	/**
	 * Constructor of the class
	 *
	 * @param categoryMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the categories.
	 */
	public CategoryManager(ConcurrentHashMap<String, Category> categoryMap) {
		this.categoryMap = categoryMap;
	}

	/**
	 * Method used to add new root category
	 *
	 * @param categoryName        Name of the new category
	 * @param categoryDescription Description of the new category
	 * @throws AlreadyExistException    Is thrown if the category or fields that are trying to add already exist
	 * @throws InvalidArgumentException Is thrown if the name or the description is blank
	 */
	public void addNewMainCategory(String categoryName, String categoryDescription) throws AlreadyExistException, InvalidArgumentException, NullObjectException {
		nameDescriptionChecker(categoryName, categoryDescription, ERROR_CATEGORY_PARAMS_NOT_VALID);

		var newMainCategory = new Category(categoryName, categoryDescription);

		isUniqueCategoryChecker(newMainCategory, ERROR_CATEGORY_ALREADY_EXISTS);

		this.categoryMap.put(newMainCategory.getUuid(), newMainCategory);

		for (JsonNode objNode : AppConfigurator.getInstance().getDefaultField()) {
			addNewField(
					new ArrayList<>(List.of("root", categoryName)),
					objNode.get("name").asText(),
					objNode.get("required").asBoolean()
			);
		}

		CategoryUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.categoryMap);
		assert this.categoryMap.containsKey(String.valueOf(newMainCategory.getUuid())) : POST_CONDITION_CATEGORY_NOT_IN_MAP;
	}

	/**
	 * Method used to add a new subcategory
	 *
	 * @param pathOfSubcategory      {@link ArrayList} that contains the path where to add the sub-category
	 * @param subCategoryName        Name of the new category
	 * @param subCategoryDescription Description of new category
	 * @throws AlreadyExistException    Is thrown if the category that are trying to add already exist
	 * @throws InvalidArgumentException Is thrown if the name is an empty string
	 * @throws NullObjectException      Is thrown if it cannot find the category in which to add the fields
	 */
	public void addNewSubCategory(ArrayList<String> pathOfSubcategory, String subCategoryName, String subCategoryDescription) throws AlreadyExistException, InvalidArgumentException, NullObjectException {
		nameDescriptionChecker(subCategoryName, subCategoryDescription, ERROR_NAME_OR_DESCRIPTION_ARE_INVALID);

		Optional<Category> fatherCategory = getCategoryFromPath(pathOfSubcategory);
		var                newSubCategory = new Category(subCategoryName.trim(), subCategoryDescription);

		if (fatherCategory.isEmpty())
			throw new NullObjectException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);

		isUniqueCategoryChecker(newSubCategory, ERROR_SUB_CATEGORY_ALREADY_EXISTS);

		fatherCategory.get().addSubCategory(newSubCategory);
		CategoryUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.categoryMap);

		assert fatherCategory.get().getSubCategory().containsKey(String.valueOf(newSubCategory.getUuid())) : POST_CONDITION_SUBCATEGORY_NOT_IN_MAP;
	}

	/**
	 * Recursive method used to add a field to a category and all its sub-categories
	 *
	 * @param pathOfCategory {@link ArrayList} that contains the path where to add the field
	 * @param fieldName      Name of the new field
	 * @param isRequired     If the field is required {@code true} otherwise {@code false}
	 * @throws AlreadyExistException    Is thrown if the new field that are trying to add already exist
	 * @throws InvalidArgumentException Is thrown if the name is an empty string
	 * @throws NullObjectException      Is thrown if it cannot find the category in which to add the fields
	 */
	public void addNewField(ArrayList<String> pathOfCategory, String fieldName, boolean isRequired)
			throws AlreadyExistException, InvalidArgumentException, NullObjectException {
		if (fieldName.trim().isBlank())
			throw new InvalidArgumentException(ERROR_INVALID_FIELD_NAME);

		Optional<Category> category = getCategoryFromPath(pathOfCategory);

		if (category.isEmpty())
			throw new NullObjectException(ERROR_NO_CATEGORY_HAS_BEEN_SELECTED);

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

		CategoryUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.categoryMap);
	}

	/**
	 * Method used to get the {@link ConcurrentHashMap} with all {@link Category categories}
	 *
	 * @return {@link ConcurrentHashMap} with all {@link Category categories}
	 */
	public ConcurrentHashMap<String, Category> getRootCategoryMap() {
		return this.categoryMap;
	}

	/**
	 * Method used to return a category by its uuid<br/>
	 * The method returns an {@link Optional}  with the {@link Category} object if the past uuid is found otherwise an empty {@link Optional}
	 *
	 * @param uuid uuid of the {@link Category} to get
	 * @return An {@link Optional} with the object {@link Category} otherwise an empty {@link Optional}
	 */
	public Optional<Category> getCategoryByUuid(String uuid) {
		return getCategory(this.categoryMap.values().stream().toList(), uuid);
	}

	private static void nameDescriptionChecker(String name, String description, String error) throws InvalidArgumentException {
		if (name.trim().isBlank() || description.isBlank())
			throw new InvalidArgumentException(error);
	}

	private void isUniqueCategoryChecker(Category newMainCategory, String error) throws AlreadyExistException {
		if (!isUniqueCategory(this.categoryMap.values().stream().toList(), newMainCategory.hashCode()))
			throw new AlreadyExistException(error);
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

		boolean result = true;

		if (!categories.isEmpty()) {
			for (Category category : categories) {
				if (category.hashCode() == hashToCheck)
					result = false;
				else {
					result = result && isUniqueCategory(category.getSubCategory().values().stream().toList(), hashToCheck);
				}
			}
		}

		return result;
	}
}
