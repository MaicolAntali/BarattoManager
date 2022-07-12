package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.category.Category;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CategoriesChangeDataEvent implements Event<String, Category> {

	public final ArrayList<DataChangeListener<String, Category>> listeners;

	public CategoriesChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener<String, Category> listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener(ConcurrentHashMap<String, Category> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
