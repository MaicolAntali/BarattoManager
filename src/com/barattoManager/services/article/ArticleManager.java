package com.barattoManager.services.article;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.category.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles and contains the data structure of the articles
 */
public class ArticleManager {

	private static final String POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP = "Post-Condition: The article is not present in the map";
	private static final String UUID_NOT_FOUND = "UUID non trovato";
	private final ConcurrentHashMap<String, Article> articleMap;

	/**
	 * Constructor of the class
	 *
	 * @param articleMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the articles.
	 */
	public ArticleManager(ConcurrentHashMap<String, Article> articleMap) {
		this.articleMap = articleMap;
	}

	/**
	 * Method used to add a new article to {@link ConcurrentHashMap}
	 *
	 * @param articleName   Name of the article
	 * @param userNameOwner Username of the article owner
	 * @param categoryUuid  Uuid of the category
	 * @param fields        {@link ArrayList} that contains all the name of the article fields
	 * @param values        {@link ArrayList} that contains all the values of the article fields
	 */
	public void addNewArticle(String articleName, String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		var article = new Article(articleName, userNameOwner, categoryUuid, fields, values);
		this.articleMap.put(article.getUuid(), article);
		ArticleUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.articleMap);

		assert this.articleMap.containsKey(article.getUuid()) : POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;
	}

	/**
	 * Method used to return an Article by its uuid<br/>
	 * The method returns an {@link Optional}  with the {@link Article} object if the past uuid is found otherwise an empty {@link Optional}
	 *
	 * @param uuid uuid of the {@link Article} to get
	 * @return An {@link Optional} with the object {@link Article} otherwise an empty {@link Optional}
	 */
	public Optional<Article> getArticleById(String uuid) {
		return Optional.ofNullable(this.articleMap.get(uuid));
	}

	/**
	 * Method used to get the {@link ConcurrentHashMap} with all {@link Article articles}
	 *
	 * @return {@link ConcurrentHashMap} with all {@link Article articles}
	 */
	public ConcurrentHashMap<String, Article> getArticleMap() {
		return articleMap;
	}

	/**
	 * Method used to change/update the status of an {@link Article}
	 *
	 * @param articleUuid Uuid of {@link Article}
	 * @param state       New {@link Article.State state} of the {@link Article}
	 * @throws InvalidArgumentException IS thrown if the article uuid is not found in the {@link #getArticleMap() articleMap}
	 */
	public void changeArticleState(String articleUuid, Article.State state) throws InvalidArgumentException {
		var article = getArticleById(articleUuid);

		if (article.isEmpty())
			throw new InvalidArgumentException(UUID_NOT_FOUND);

		article.get().setArticleState(state);
		ArticleUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.articleMap);
	}

	/**
	 * Method used to return a filtered {@link Article} {@link List} by owner, status and category
	 *
	 * @param ownerFilter  Filter on the owner’s username
	 * @param state        Filter on the status of the article
	 * @param categoryUuid Filter on the uuid of the category
	 * @return filtered {@link Article} {@link List}
	 */
	public List<Article> getArticlesByOwnerStateCategory(String ownerFilter, Article.State state, String categoryUuid) {
		return this.articleMap.values().stream()
				.filter(article -> Objects.equals(article.getUserNameOwner().toLowerCase(), ownerFilter.toLowerCase()))
				.filter(article -> article.getArticleState() == state)
				.filter(article -> Objects.equals(article.getCategoryUuid(), categoryUuid))
				.toList();
	}
}
