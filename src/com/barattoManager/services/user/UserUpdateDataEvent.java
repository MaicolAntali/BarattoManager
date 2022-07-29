package com.barattoManager.services.user;

import com.barattoManager.services.event.UpdateDataHandler;
import com.barattoManager.services.event.UpdateDataListener;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class UserUpdateDataEvent implements UpdateDataHandler<String, User> {

	private final ArrayList<UpdateDataListener<String, User>> listeners;

	public UserUpdateDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(UpdateDataListener<String, User> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireUpdateListeners(ConcurrentHashMap<String, User> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
