package com.barattoManager.ui.panels.categoryTree;

import com.barattoManager.category.CategoryManager;

import java.util.ArrayList;

/**
 * This class is a <b>Singleton Class</b><br/> used to add and fire {@link RepaintingEventHandler} event.
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

	public void addListener(RepaintingEventHandler toAdd) {
		listeners.add(toAdd);
	}

	public void fireListeners() {
		for (RepaintingEventHandler event : listeners) {
			event.repaintTreeContainerComponents();
		}
	}
}
