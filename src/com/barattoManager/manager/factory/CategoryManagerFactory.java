package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.CategoryManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.category.Category;
import com.barattoManager.utils.AppConfigurator;

public class CategoryManagerFactory {

	private static final CategoryManager CATEGORY_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, Category>(
				AppConfigurator.getInstance().getFileName("categories")
		);

		CATEGORY_MANAGER = new CategoryManager(jsonHandler.readJson(String.class, Category.class));

		EventFactory.getCategoriesEvent().addListener(jsonHandler);
	}

	public static CategoryManager getManager() {
		return CATEGORY_MANAGER;
	}
}
