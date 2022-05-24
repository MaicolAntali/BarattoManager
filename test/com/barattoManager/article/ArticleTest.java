package com.barattoManager.article;

import com.barattoManager.category.Category;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.user.viewer.Viewer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

	@Test
	void badFieldValueMap() {
		assertThrows(IllegalValuesException.class, ()-> new Article(
				"Luca",
				new Category("Libro", "...").getUuid(),
				new ArrayList<>() {{
					add(new Field("Titolo", true));
					add(new Field("Pagine", true));
					add(new Field("Autore", true));

				}},
				new ArrayList<>() {{
					add("Il Trono di Spade");
					add("690");
					add("");

				}}
		));
	}

}