package com.barattoManager.services.article;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Class that constructs the {@link ArticleManager}<br/>
 * {@link ArticleManager}is declared in the class as a static field, to ensure one instance for the whole project.
 * It implements {@link Runnable} because it is run in a separated thread
 */
public class ArticleManagerFactory implements Runnable {

	private static ArticleManager ARTICLE_MANAGER;

	public static ArticleManager getManager() {
		return ARTICLE_MANAGER;
	}

	@Override
	public void run() {
		var jsonHandler = new JsonHandler<String, Article>(
				AppConfigurator.getInstance().getFileName("articles"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		ArticleUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		ARTICLE_MANAGER = new ArticleManager(jsonHandler.readJson(String.class, Article.class));
	}
}
