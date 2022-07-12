package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.article.Article;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Class that constructs the {@link ArticleManager}<br/>
 * {@link ArticleManager}is declared in the class as a static constant, to ensure one instance for the whole project.
 */
public class ArticleManagerFactory {

	private static final ArticleManager ARTICLE_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, Article>(
				AppConfigurator.getInstance().getFileName("articles"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		EventFactory.getArticlesEvent().addListener(jsonHandler);

		ARTICLE_MANAGER = new ArticleManager(jsonHandler.readJson(String.class, Article.class));
	}

	/**
	 * Method used to get the instance of {@link ArticleManager} stored in the class.
	 *
	 * @return The {@link ArticleManager} object
	 */
	public static ArticleManager getManager() {
		return ARTICLE_MANAGER;
	}
}
