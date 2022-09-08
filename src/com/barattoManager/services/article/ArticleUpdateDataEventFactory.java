package com.barattoManager.services.article;

import com.barattoManager.services.event.UpdateDataHandler;

/**
 * Class that constructs the {@link ArticleUpdateDataEvent}<br/>
 * {@link ArticleUpdateDataEvent}is declared in the class as a static field, to ensure one instance for the whole project.
 */
public class ArticleUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Article> EVENT_HANDLER = new ArticleUpdateDataEvent();

	public static UpdateDataHandler<String, Article> getEventHandler() {
		return EVENT_HANDLER;
	}
}
