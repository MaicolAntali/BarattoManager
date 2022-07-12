package com.barattoManager.manager;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.field.Field;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleManagerTest {

	private static ArticleManager articleManagerInstance;

	@BeforeAll
	static void setUp() {
		articleManagerInstance = new ArticleManager(
				new JsonHandler<String, Article>(
						"test/json/articles.json",
						JsonMapper.builder()
								.addModule(new ParameterNamesModule())
								.addModule(new Jdk8Module())
								.addModule(new JavaTimeModule())
								.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
								.build()
				)
						.readJson(String.class, Article.class)
		);
	}

	@Test
	@Order(1)
	void getArticles() {
		assertEquals(2, articleManagerInstance.getArticleMap().size());
	}

	@Test
	@Order(1)
	void getArticleByOwnerStateCategory() {
		assertEquals(1, articleManagerInstance.getArticlesByOwnerStateCategory("Luca", Article.State.OPEN_OFFER, "671c4f25-1a72-4f49-8436-76a2a7d0cd90").size());
	}

	@Test
	void getArticleWrongUuid() {
		assertEquals(Optional.empty(), articleManagerInstance.getArticleById("wrong-uuid"));
	}

	@Test
	void getArticle() {
		assertFalse(articleManagerInstance.getArticleById("a59dd6ed-2106-4969-8a23-ffbec5d37a20").isEmpty());
	}

	@Test
	void addNewArticle() {
		articleManagerInstance.addNewArticle(
				"TEST",
				"OWNER_TEST",
				"UUID_CATEGORY",
				new ArrayList<>(List.of(new Field("TEST_FIELD", true))),
				new ArrayList<>(List.of("TEST_FIELD_VALUE"))
		);

		assertEquals(3, articleManagerInstance.getArticleMap().size());
	}

	@Test
	void changeArticleStateWrongUuid() {
		assertThrows(IllegalValuesException.class, () -> articleManagerInstance.changeArticleState("wrong-uuid", Article.State.OPEN_OFFER));
	}

	@Test
	void changeArticleState() {
		try {
			articleManagerInstance.changeArticleState("a59dd6ed-2106-4969-8a23-ffbec5d37a20", Article.State.CLOSE_OFFER);
		} catch (IllegalValuesException e) {
			throw new RuntimeException(e);
		}

		assertEquals(Article.State.CLOSE_OFFER, articleManagerInstance.getArticleById("a59dd6ed-2106-4969-8a23-ffbec5d37a20").orElseThrow().getArticleState());
	}
}