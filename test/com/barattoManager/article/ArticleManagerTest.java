package com.barattoManager.article;

import com.barattoManager.category.Category;
import com.barattoManager.category.field.Field;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ArticleManagerTest {

	@Test
	void test() {
		ArticleManager.getInstance().addNewArticle(
				"Luca",
				new Category("Libro", "...").getUuid(),
				new HashMap<>() {{
					put(new Field("Titolo", true), "Promessi Sposi");
					put(new Field("Pagine", true), "720");
					put(new Field("Autore", true), "Alessandro Manzoni");

				}}
		);
	}
}