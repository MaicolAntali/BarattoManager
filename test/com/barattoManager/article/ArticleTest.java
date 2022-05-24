package com.barattoManager.article;

import com.barattoManager.category.Category;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.user.viewer.Viewer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

	@Test
	void badFieldValueMap() {
		assertThrows(IllegalValuesException.class, ()-> new Article(
				"Luca",
				new Category("Libro", "...").getUuid(),
				new HashMap<>() {{
					put(new Field("1-required", true), "OK");
					put(new Field("2-required", true), "OK");
					put(new Field("3-required-ERROR", true), "");

				}}
		));
	}

}