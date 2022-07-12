package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.barattoManager.manager.Constants.POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;

public final class ArticleManager implements Manager {

	private final ConcurrentHashMap<String, Article> articleMap;

	public ArticleManager(ConcurrentHashMap<String, Article> articleMap) {
		this.articleMap = articleMap;
	}

	/**
	 * Method used to add new article
	 *
	 * @param articleName   Article name
	 * @param userNameOwner Owner name of article
	 * @param categoryUuid  Category uuid of article
	 * @param fields        {@link ArrayList} that contains fields name of article
	 * @param values        {@link ArrayList} that contains fields values
	 */
	public void addNewArticle(String articleName, String userNameOwner, String categoryUuid, ArrayList<Field> fields, ArrayList<String> values) {
		var article = new Article(articleName, userNameOwner, categoryUuid, fields, values);
		this.articleMap.put(article.getUuid(), article);
		EventFactory.getArticlesEvent().fireListener(this.articleMap);

		assert this.articleMap.containsKey(article.getUuid()) : POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;
	}

	/**
	 * Method used to get an article by the UUID
	 *
	 * @param uuid {@link java.util.UUID} of the article
	 * @return {@link Optional} of {@link Article}
	 */
	public Optional<Article> getArticleById(String uuid) {
		return Optional.ofNullable(this.articleMap.get(uuid));
	}

	public ConcurrentHashMap<String, Article> getArticleMap() {
		return articleMap;
	}

	public void changeArticleState(String articleUuid, Article.State state) throws IllegalValuesException {
		var article = getArticleById(articleUuid);

		if (article.isEmpty())
			throw new IllegalValuesException("UUID non trovato");

		article.get().setArticleState(state);
		EventFactory.getArticlesEvent().fireListener(this.articleMap);
	}

	/**
	 * Method used to get the {@link Article}
	 *
	 * @param ownerFilter  Owner filter
	 * @param state        The state of the article
	 * @param categoryUuid UUID of the category
	 * @return {@link List} of articles
	 */
	public List<Article> getArticlesByOwnerStateCategory(String ownerFilter, Article.State state, String categoryUuid) {
		return this.articleMap.values().stream()
				.filter(article -> Objects.equals(article.getUserNameOwner().toLowerCase(), ownerFilter.toLowerCase()))
				.filter(article -> article.getArticleState() == state)
				.filter(article -> Objects.equals(article.getCategoryUuid(), categoryUuid))
				.toList();
	}
}

