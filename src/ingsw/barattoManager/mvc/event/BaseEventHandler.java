package ingsw.barattoManager.mvc.event;

import java.util.ArrayList;

public class BaseEventHandler {

	private final ArrayList<BaseEventListeners> listeners;

	public BaseEventHandler() {
		this.listeners = new ArrayList<>();
	}

	public void addListeners(BaseEventListeners baseEventListeners){
		listeners.add(baseEventListeners);
	}

	public void fireListener(Event event) {
		listeners.forEach(baseEventListeners -> baseEventListeners.eventPerformed(event));
	}
}
