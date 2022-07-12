package com.barattoManager.event.events;

import java.util.concurrent.ConcurrentHashMap;

public interface DataChangeListener<K, V> {
	void update(ConcurrentHashMap<K, V> updatedMap);

}