package com.barattoManager.ui.customComponents.menu.factory;

import com.barattoManager.ui.customComponents.menu.Menu;
import com.barattoManager.ui.customComponents.menu.StoreMenu;

public class StoreMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new StoreMenu();
	}
}
