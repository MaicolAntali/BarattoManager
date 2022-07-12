package com.barattoManager.ui.customComponents.menu.factory;

import com.barattoManager.ui.customComponents.menu.Menu;
import com.barattoManager.ui.customComponents.menu.StoreMenu;
import com.barattoManager.ui.customComponents.menu.TradeMenu;

/**
 * Factory used to generate a {@link StoreMenu}
 */
public class StoreMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new StoreMenu();
	}
}
