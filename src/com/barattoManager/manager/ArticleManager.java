package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.field.Field;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.barattoManager.manager.Constants.POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the articles.
 */
public final class ArticleManager extends NoConcurrencyManager<String, Article>{

	/**
	 * {@link ArticleManager} constructor
	 */
	private ArticleManager() {
		super(String.class, Article.class);
	}

	@Override
	File getJsonFile() {
		return new File(AppConfigurator.getInstance().getFileName("article_file"));
	}

	@Override
	ObjectMapper getObjectMapper() {
		return JsonMapper.builder()
				.addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module())
				.addModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
	}

	@Override
	void afterDataChangeActions() {
		EventFactory.getArticlesEvent().fireListener();
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
		getDataMap().put(article.getUuid(), article);
		saveDataMap();

		assert getDataMap().containsKey(article.getUuid()) : POST_CONDITION_THE_ARTICLE_IS_NOT_PRESENT_IN_THE_MAP;
	}

	public Optional<Article> getArticleById(String uuid) {
		return Optional.ofNullable(getDataMap().get(uuid));
	}

	public List<Article> getArticles() {
		return new ArrayList<>(getDataMap().values());
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
}

