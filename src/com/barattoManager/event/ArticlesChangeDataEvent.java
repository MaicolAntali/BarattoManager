package com.barattoManager.event;

import com.barattoManager.old.sample.article.Article;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles events related to articles data change.<br/>
 */
public class ArticlesChangeDataEvent implements Event<String, Article> {

	private final ArrayList<DataChangeListener<String, Article>> listeners;

	/**
	 * Constructor of the class.<br/>
	 * it's used to initialize listeners arraylist
	 *
	 * @see #listeners
	 */
	public ArticlesChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener<String, Article> listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener(ConcurrentHashMap<String, Article> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
