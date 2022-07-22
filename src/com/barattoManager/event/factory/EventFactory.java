package com.barattoManager.event.factory;

import com.barattoManager.event.*;
import com.barattoManager.old.sample.article.Article;
import com.barattoManager.old.sample.category.Category;
import com.barattoManager.old.sample.meet.Meet;
import com.barattoManager.old.sample.trade.Trade;
import com.barattoManager.old.sample.user.User;

/**
 * Class that constructs all kinds of events that the system needs.<br/>
 * Each event is declared in the class as a static constant, to ensure one instance for the whole project.
 */
public class EventFactory {

	private static final Event<String, User> USER_EVENT = new UserChangeDataEvent();
	private static final Event<String, Category> CATEGORY_EVENT = new CategoriesChangeDataEvent();
	private static final Event<String, Meet> MEET_EVENT = new MeetsChangeDataEvent();
	private static final Event<String, Article> ARTICLE_EVENT = new ArticlesChangeDataEvent();
	private static final Event<String, Trade> TRADE_EVENT = new TradesChangeDataEvent();


	/**
	 * Method used to get the instance of {@link UserChangeDataEvent} stored in the class.
	 *
	 * @return The {@link UserChangeDataEvent} as an {@link Event}
	 */
	public static Event<String, User> getUsersEvent() {
		return USER_EVENT;
	}

	/**
	 * Method used to get the instance of {@link CategoriesChangeDataEvent} stored in the class.
	 *
	 * @return The {@link CategoriesChangeDataEvent} as an {@link Event}
	 */
	public static Event<String, Category> getCategoriesEvent() {
		return CATEGORY_EVENT;
	}

	/**
	 * Method used to get the instance of {@link MeetsChangeDataEvent} stored in the class.
	 *
	 * @return The {@link MeetsChangeDataEvent} as an {@link Event}
	 */
	public static Event<String, Meet> getMeetsEvent() {
		return MEET_EVENT;
	}

	/**
	 * Method used to get the instance of {@link ArticlesChangeDataEvent} stored in the class.
	 *
	 * @return The {@link ArticlesChangeDataEvent} as an {@link Event}
	 */
	public static Event<String, Article> getArticlesEvent() {
		return ARTICLE_EVENT;
	}

	/**
	 * Method used to get the instance of {@link TradesChangeDataEvent} stored in the class.
	 *
	 * @return The {@link TradesChangeDataEvent} as an {@link Event}
	 */
	public static Event<String, Trade> getTradesEvent() {
		return TRADE_EVENT;
	}
}
