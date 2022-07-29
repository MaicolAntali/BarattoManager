package com.barattoManager.services.event;

import java.util.concurrent.ConcurrentHashMap;

public interface UpdateDataListener<K, V> {

	void update(ConcurrentHashMap<K, V> updatedMap);
}
