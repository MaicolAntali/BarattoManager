package com.barattoManager.manager;

import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.exception.NullCategoryException;
import com.barattoManager.old.manager.CategoryManager;
import com.barattoManager.old.sample.category.Category;
import ingsw.barattoManager.mvc.models.json.JsonHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryManagerTest {

	private static CategoryManager categoryManagerInstance;

	@BeforeAll
	static void setUp() {
		categoryManagerInstance = new CategoryManager(
				new JsonHandler<String, Category>("test/json/categories.json")
						.readJson(String.class, Category.class)
		);
	}

	@Test
	void addMainCategoryWithoutName() {
		assertThrows(IllegalValuesException.class, () ->  categoryManagerInstance.addNewMainCategory("", "..."));
	}

	@Test
	void addMainCategoryWithoutDescription() {
		assertThrows(IllegalValuesException.class, () ->  categoryManagerInstance.addNewMainCategory("Test", ""));
	}

	@Test
	void addExistingMainCategory() {
		assertThrows(AlreadyExistException.class, () ->  categoryManagerInstance.addNewMainCategory("Libri", "..."));
	}

	@Test
	void addSubCategoryWithoutName() {
		assertThrows(IllegalValuesException.class, () ->  categoryManagerInstance.addNewSubCategory(new ArrayList<>(List.of("root", "Libri")),"", "..."));
	}

	@Test
	void addMainSubCategoryWithoutDescription() {
		assertThrows(IllegalValuesException.class, () ->  categoryManagerInstance.addNewSubCategory(new ArrayList<>(List.of("root", "Libri")),"Test", ""));
	}

	@Test
	void addExistingSubCategory() {
		assertThrows(AlreadyExistException.class, () ->  categoryManagerInstance.addNewSubCategory(new ArrayList<>(List.of("root", "Libri")),"Romanzi", "..."));
	}

	@Test
	void passWrongSubCategoryPath() {
		assertThrows(NullCategoryException.class, () ->  categoryManagerInstance.addNewSubCategory(new ArrayList<>(List.of("root", "...")),"Test", "..."));
	}

	@Test
	void addNewFieldWithoutName() {
		assertThrows(IllegalValuesException.class, () -> categoryManagerInstance.addNewField(new ArrayList<>(List.of("root", "Libri")), "", true));
	}

	@Test
	void addExistingFieldMainCategory() {
		assertThrows(AlreadyExistException.class, () -> categoryManagerInstance.addNewField(new ArrayList<>(List.of("root", "Libri")), "pagine", true));
	}

	@Test
	void addExistingFieldSubCategory() {
		assertThrows(AlreadyExistException.class, () -> categoryManagerInstance.addNewField(new ArrayList<>(List.of("root", "Libri", "Romanzi", "Gialli")), "pagine", true));
	}

	@Test
	void passWrongFieldPath() {
		assertThrows(NullCategoryException.class, () -> categoryManagerInstance.addNewField(new ArrayList<>(List.of("root", "...")), "Test", true));
	}

	@Test
	void addMainCategory() {
		try {
			categoryManagerInstance.addNewMainCategory("TestMainCategory", "...");
		} catch (AlreadyExistException | IllegalValuesException | NullCategoryException e) {
			throw new RuntimeException(e);
		}

		assertEquals(2, categoryManagerInstance.getRootCategoryMap().size());
	}

	@Test
	void addSubCategory() {
		try {
			categoryManagerInstance.addNewSubCategory(new ArrayList<>(List.of("root", "Libri")), "TestSubCategory", "...");
		} catch (AlreadyExistException | IllegalValuesException | NullCategoryException e) {
			throw new RuntimeException(e);
		}

		categoryManagerInstance.getCategoryByUuid("e7db3ac3-e8cb-42,23-9b87-f3a2c0c78dd9")
				.ifPresent(category -> assertEquals(2, category.getSubCategory().size()));
	}

	@Test
	void addField() {
		try {
			categoryManagerInstance.addNewField(new ArrayList<>(List.of("root", "Libri")), "TestField", false);
		} catch (AlreadyExistException | IllegalValuesException | NullCategoryException e) {
			throw new RuntimeException(e);
		}

		categoryManagerInstance.getCategoryByUuid("e7db3ac3-e8cb-42,23-9b87-f3a2c0c78dd9")
				.ifPresent(category -> assertEquals(4, category.getCategoryFields().size()));
		categoryManagerInstance.getCategoryByUuid("690b72b7-2cc6-4056-843c-355dc32512da")
				.ifPresent(category -> assertEquals(4, category.getCategoryFields().size()));
	}
}