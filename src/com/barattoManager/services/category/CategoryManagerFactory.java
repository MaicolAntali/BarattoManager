package com.barattoManager.services.category;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;

public class CategoryManagerFactory implements Runnable {

	private static CategoryManager CATEGORY_MANAGER;

	public static CategoryManager getManager() {
		return CATEGORY_MANAGER;
	}

	@Override
	public void run() {
		var jsonHandler = new JsonHandler<String, Category>(AppConfigurator.getInstance().getFileName("categories"));

		CategoryUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		CATEGORY_MANAGER = new CategoryManager(jsonHandler.readJson(String.class, Category.class));
	}
}
