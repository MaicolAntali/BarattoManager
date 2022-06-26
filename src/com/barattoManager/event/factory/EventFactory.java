package com.barattoManager.event.factory;

import com.barattoManager.event.*;

import java.util.HashMap;

public class EventFactory {

	private static final HashMap<String, Event> events = new HashMap<>();

	static {
		events.put("categories", new CategoriesChangeDataEvent());
		events.put("meets", new MeetsChangeDataEvent());
		events.put("articles", new ArticlesChangeDataEvent());
		events.put("trades", new TradesChangeDataEvent());
	}

	public static Event getArticlesEvent() {
		return events.get("articles");
	}

	public static Event getTradesEvent() {
		return events.get("trades");
	}
	public static Event getCategoriesEvent() {
		return events.get("categories");
	}
	public static Event getMeetsEvent() {
		return events.get("meets");
	}
}
