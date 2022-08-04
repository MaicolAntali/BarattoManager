package com.barattoManager.services.category;

import com.barattoManager.services.event.UpdateDataHandler;
import com.barattoManager.services.event.UpdateDataListener;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryUpdateDataEvent implements UpdateDataHandler<String, Category> {

	private final ArrayList<UpdateDataListener<String, Category>> listeners;

	public CategoryUpdateDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(UpdateDataListener<String, Category> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireUpdateListeners(ConcurrentHashMap<String, Category> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
