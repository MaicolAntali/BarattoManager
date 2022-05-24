package com.barattoManager.article;

import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ArticleManagerTest {

	@Test
	void test() {
		ArticleManager.getInstance().addNewArticle(
				"Luca",
				"719e26a0-9fe5-44b5-af28-0b29c9aa5880",
				new ArrayList<>() {{
					add(new Field("Titolo", true));
					add(new Field("Pagine", true));
					add(new Field("Autore", true));
					add(new Field("Editore", false));

				}},
				new ArrayList<>() {{
					add("Dante Alighieri");
					add("");
					add("Divina Commedia");
					add("");
				}}
		);
	}
}