package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.event.ShowControllerListener;

import java.util.ArrayList;

public class ShowControllerHandler {

	private final ArrayList<ShowControllerListener> listeners;

	public ShowControllerHandler() {
		listeners = new ArrayList<>();
	}

	public void addListener(ShowControllerListener listener) {
		this.listeners.add(listener);
	}

	public void fireShowListeners(String controllerName) {
		this.listeners.forEach(listener -> listener.show(controllerName));
	}
}
