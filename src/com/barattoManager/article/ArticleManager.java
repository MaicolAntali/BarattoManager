package com.barattoManager.article;

import com.barattoManager.category.field.Field;
import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.IllegalValuesException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArticleManager {
	/**
	 * Category JSON file
	 */
	private final File articleFile = new File(AppConfigurator.getInstance().getFileName("article_file"));
	/**
	 * {@link ObjectMapper} object, used to parse JSON
	 */
	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();
	/**
	 * {@link HashMap} that contain the articles
	 */
	private final HashMap<String, Article> articleMap;

	private ArticleManager() {
		if (articleFile.exists()) {
			try {
				articleMap = objectMapper.readValue(articleFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			articleMap = new HashMap<>();
		}
	}

	/**
	 * Holder class of instance
	 */
	private static final class ArticleManagerHolder {
		/**
		 * Instance of {@link ArticleManager}
		 */
		private static final ArticleManager instance = new ArticleManager();
	}

	/**
	 * Method used to create get the {@link ArticleManager} instance.
	 * This method uses the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code #getInstance()} method is called.
	 *
	 * @return The Instance of {@link ArticleManager} class
	 */
	public static ArticleManager getInstance() {
		return ArticleManagerHolder.instance;
	}

	public void addNewArticle(String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		Article article;

		try {
			article = new Article(userNameOwner, categoryUuid, fields, values);
		} catch (IllegalValuesException e) {
			throw new RuntimeException(e);
		}

		articleMap.put(article.getUuid(), article);
		saveArticleMapChange();
	}

	/**
	 * Method used to save in the json file the {@link #articleMap} object
	 */
	private void saveArticleMapChange() {
		try {
			objectMapper.writeValue(articleFile, articleMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

