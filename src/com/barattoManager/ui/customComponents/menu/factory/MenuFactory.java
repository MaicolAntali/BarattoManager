package com.barattoManager.ui.customComponents.menu.factory;

import com.barattoManager.ui.customComponents.menu.Menu;

/**
 * Menu Factory
 */
public interface MenuFactory {
	/**
	 * Method used to create a Menu object
	 *
	 * @return {@link Menu}
	 */
	Menu createMenuObject();
}
