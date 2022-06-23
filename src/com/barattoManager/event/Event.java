package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

public interface Event {
	void addListener(DataChangeListener listener);
	void removeListener(DataChangeListener listener);
	void fireListener();
}
