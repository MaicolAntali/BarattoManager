package com.barattoManager.services.article;

import com.barattoManager.services.event.UpdateDataHandler;

public class ArticleUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Article> EVENT_HANDLER = new ArticleUpdateDataEvent();

	public static UpdateDataHandler<String, Article> getEventHandler() {
		return EVENT_HANDLER;
	}
}
