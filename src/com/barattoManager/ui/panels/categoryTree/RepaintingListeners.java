package com.barattoManager.ui.panels.categoryTree;

import com.barattoManager.category.CategoryManager;

import java.util.ArrayList;

/**
 * This class is a <b>Singleton Class</b><br/> used to add and fire {@link RepaintingEventHandler} events.
 */
public class RepaintingListeners {

	private final ArrayList<RepaintingEventHandler> listeners;

	private RepaintingListeners() {
		listeners = new ArrayList<>();
	}


	private static final class RepaintingListenersHolder {
		private static final RepaintingListeners instance = new RepaintingListeners();
	}

	/**
	 * Method used to create get the {@link RepaintingListeners} instance.
	 * This method uses the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code #getInstance()} method is called.
	 *
	 * @return The Instance of {@link CategoryManager} class
	 */
	public static RepaintingListeners getInstance() {
		return RepaintingListenersHolder.instance;
	}

	/**
	 * Method used to add a new {@link RepaintingListeners} in the {@link #listeners} List.
	 * @param toAdd {@link RepaintingListeners} to add in the List
	 */
	public void addListener(RepaintingEventHandler toAdd) {
		listeners.add(toAdd);
	}

	/**
	 * Method used to fire for each {@link #listeners} the method {@link RepaintingEventHandler#repaintTreeContainerComponents()}
	 */
	public void fireListeners() {
		for (RepaintingEventHandler event : listeners) {
			event.repaintTreeContainerComponents();
		}
	}
}
