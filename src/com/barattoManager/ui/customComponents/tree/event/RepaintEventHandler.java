package com.barattoManager.ui.customComponents.tree.event;

import java.util.ArrayList;

/**
 * Class used to add and fire {@link RepaintListener} events.
 */
public class RepaintEventHandler {

	/**
	 * {@link ArrayList} of listeners
	 */
	private final ArrayList<RepaintListener> listeners;

	/**
	 * {@link RepaintEventHandler} constructor
	 */
	public RepaintEventHandler() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method used to add a new {@link RepaintEventHandler} in the {@link #listeners} List.
	 * @param toAdd {@link RepaintEventHandler} to add in the List
	 */
	public void addListener(RepaintListener toAdd) {
		listeners.add(toAdd);
	}

	/**
	 * Method used to fire for each {@link #listeners} the method {@link RepaintListener#repaintComponents()}
	 */
	public void fireListeners() {
		for (RepaintListener event : listeners) {
			event.repaintComponents();
		}
	}
}
