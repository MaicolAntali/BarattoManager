package com.barattoManager.event.factory;

import com.barattoManager.event.*;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.Category;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.user.User;

public class EventFactory {

	private static final Event<String, User> USER_EVENT = new UserChangeDataEvent();
	private static final Event<String, Category> CATEGORY_EVENT = new CategoriesChangeDataEvent();
	private static final Event<String, Meet> MEET_EVENT = new MeetsChangeDataEvent();
	private static final Event<String, Article> ARTICLE_EVENT = new ArticlesChangeDataEvent();
	private static final Event<String, Trade> TRADE_EVENT = new TradesChangeDataEvent();


	public static Event<String, User> getUsersEvent() {
		return USER_EVENT;
	}

	public static Event<String, Category> getCategoriesEvent() {
		return CATEGORY_EVENT;
	}

	public static Event<String, Meet> getMeetsEvent() {
		return MEET_EVENT;
	}

	public static Event<String, Article> getArticlesEvent() {
		return ARTICLE_EVENT;
	}

	public static Event<String, Trade> getTradesEvent() {
		return TRADE_EVENT;
	}
}
