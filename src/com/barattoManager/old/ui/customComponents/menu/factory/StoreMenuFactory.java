package com.barattoManager.old.ui.customComponents.menu.factory;

import com.barattoManager.old.ui.customComponents.menu.Menu;
import com.barattoManager.old.ui.customComponents.menu.StoreMenu;

/**
 * Factory used to generate a {@link StoreMenu}
 */
public class StoreMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new StoreMenu();
	}
}
