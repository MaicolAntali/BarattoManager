package com.barattoManager.ui.customComponents.menu.factory;

import com.barattoManager.ui.customComponents.menu.DashBoardMenu;
import com.barattoManager.ui.customComponents.menu.Menu;

/**
 * Factory used to generate a {@link DashBoardMenu}
 */
public class DashboardMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new DashBoardMenu();
	}
}
