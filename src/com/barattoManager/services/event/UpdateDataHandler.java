package com.barattoManager.services.event;

import java.util.concurrent.ConcurrentHashMap;

public interface UpdateDataHandler<K, V> {

	void addListener(UpdateDataListener<K, V> listener);
	void fireUpdateListeners(ConcurrentHashMap<K, V> updatedMap);
}
