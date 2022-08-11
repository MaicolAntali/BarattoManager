package com.barattoManager.services.article;

import com.barattoManager.services.event.UpdateDataHandler;
import com.barattoManager.services.event.UpdateDataListener;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ArticleUpdateDataEvent implements UpdateDataHandler<String, Article> {

	private final ArrayList<UpdateDataListener<String, Article>> listeners;

	public ArticleUpdateDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(UpdateDataListener<String, Article> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireUpdateListeners(ConcurrentHashMap<String, Article> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
