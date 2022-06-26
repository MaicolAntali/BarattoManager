package com.barattoManager.event.factory;

import com.barattoManager.event.ArticlesChangeDataEvent;
import com.barattoManager.event.Event;

public class EventFactory {

	private static final Event articlesChangeDataEvent = new ArticlesChangeDataEvent();


	public static Event getArticlesEvent() {
		return articlesChangeDataEvent;
	}
}
