package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.user.User;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class UserChangeDataEvent implements Event<String, User> {

	public final ArrayList<DataChangeListener<String, User>> listeners;

	public UserChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener<String, User> listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener(ConcurrentHashMap<String, User> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
