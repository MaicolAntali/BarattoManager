package com.barattoManager.ui.customComponents.menu.factory;

import com.barattoManager.ui.customComponents.menu.DashBoardMenu;
import com.barattoManager.ui.customComponents.menu.Menu;

public class DashboardMenuFactory implements MenuFactory {
	@Override
	public Menu createMenuObject() {
		return new DashBoardMenu();
	}
}
