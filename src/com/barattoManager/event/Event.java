package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

import java.util.concurrent.ConcurrentHashMap;

public interface Event<K, V> {
	void addListener(DataChangeListener<K, V> listener);

	void fireListener(ConcurrentHashMap<K, V> updatedMap);
}
