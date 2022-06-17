package com.barattoManager.article;

import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.config.AppConfigurator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the articles.
 */
public class ArticleManager {
	/**
	 * Post-Condition: The article is not present in the map
	 */
	private static final String POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP = "Post-Condition: The article is not present in the map";
	/**
	 * Article JSON file
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

	/**
	 * {@link ArticleManager} constructor
	 */
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

	/**
	 * Method used to add new article
	 *
	 * @param userNameOwner Owner name of article
	 * @param categoryUuid Category uuid of article
	 * @param fields {@link ArrayList} that contains fields name of article
	 * @param values {@link ArrayList} that contains fields values
	 */
	public void addNewArticle(String articleName, String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		var article = new Article(articleName, userNameOwner, categoryUuid, fields, values);
		articleMap.put(article.getUuid(), article);
		saveArticleMapChange();

		assert articleMap.containsKey(article.getUuid()) : POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;
	}

	public Optional<Article> getArticleById(String uuid) {
		return Optional.ofNullable(articleMap.get(uuid));
	}

	public List<Article> getArticles() {
		return new ArrayList<>(articleMap.values());
	}

	public List<Article> getArticlesByOwner(String ownerFilter) {
		return getArticles().stream()
				.filter(article -> Objects.equals(article.getUserNameOwner().toLowerCase(), ownerFilter.toLowerCase()))
				.collect(Collectors.toList());
	}

	public List<Article> getArticlesByOwnerStateCategory(String ownerFilter, Article.State state, String categoryUuid) {
		return getArticlesByOwner(ownerFilter).stream()
				.filter(article -> article.getArticleState() == state)
				.filter(article -> Objects.equals(article.getCategoryUuid(), categoryUuid))
				.collect(Collectors.toList());
	}

	public List<Article> getArticlesByStatusExceptOwner(Article.State stateFilter, String ownerFilter) {
		return getArticles().stream()
				.filter(article -> article.getArticleState() == stateFilter)
				.filter(article -> !Objects.equals(article.getUserNameOwner().toLowerCase(), ownerFilter.toLowerCase()))
				.collect(Collectors.toList());
	}

	/**
	 * Method used to save in the json file the {@link #articleMap} object
	 */
	protected void saveArticleMapChange() {
		try {
			objectMapper.writeValue(articleFile, articleMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

