package com.barattoManager.event.factory;

import com.barattoManager.event.ArticlesChangeDataEvent;

public class EventFactory {

	private static final ArticlesChangeDataEvent articlesChangeDataEvent = new ArticlesChangeDataEvent();


	public static ArticlesChangeDataEvent getArticlesEvent() {
		return articlesChangeDataEvent;
	}
}
