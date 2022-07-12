package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.article.Article;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ArticlesChangeDataEvent implements Event<String, Article> {

	public final ArrayList<DataChangeListener<String, Article>> listeners;

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
