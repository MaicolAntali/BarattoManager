package com.barattoManager.event.factory;

import com.barattoManager.event.*;

import java.util.HashMap;

/**
 * Factory of events
 */
public class EventFactory {

	/**
	 * {@link HashMap} of events
	 */
	private static final HashMap<String, Event> events = new HashMap<>();

	static {
		events.put("categories", new CategoriesChangeDataEvent());
		events.put("meets", new MeetsChangeDataEvent());
		events.put("articles", new ArticlesChangeDataEvent());
		events.put("trades", new TradesChangeDataEvent());
	}

	/**
	 * Method used to get the articles change data event
	 * @return The {@link ArticlesChangeDataEvent}
	 */
	public static Event getArticlesEvent() {
		return events.get("articles");
	}

	/**
	 * Method used to get the trades change data event
	 * @return The {@link TradesChangeDataEvent}
	 */
	public static Event getTradesEvent() {
		return events.get("trades");
	}

	/**
	 * Method used to get the categories change data event
	 * @return The {@link CategoriesChangeDataEvent}
	 */
	public static Event getCategoriesEvent() {
		return events.get("categories");
	}

	/**
	 * Method used to get the meets change data event
	 * @return The {@link MeetsChangeDataEvent}
	 */
	public static Event getMeetsEvent() {
		return events.get("meets");
	}
}
