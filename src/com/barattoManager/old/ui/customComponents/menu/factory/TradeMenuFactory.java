package com.barattoManager.old.ui.customComponents.menu.factory;

import com.barattoManager.old.ui.customComponents.menu.Menu;
import com.barattoManager.old.ui.customComponents.menu.TradeMenu;

/**
 * Factory used to generate a {@link TradeMenu}
 */
public class TradeMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new TradeMenu();
	}
}
