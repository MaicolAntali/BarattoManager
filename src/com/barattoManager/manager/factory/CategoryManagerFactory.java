package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.CategoryManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.category.Category;
import com.barattoManager.utils.AppConfigurator;

/**
 * Class that constructs the {@link CategoryManager}<br/>
 * {@link CategoryManager}is declared in the class as a static constant, to ensure one instance for the whole project.
 */
public class CategoryManagerFactory {

	private static final CategoryManager CATEGORY_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, Category>(
				AppConfigurator.getInstance().getFileName("categories")
		);

		EventFactory.getCategoriesEvent().addListener(jsonHandler);

		CATEGORY_MANAGER = new CategoryManager(jsonHandler.readJson(String.class, Category.class));
	}

	/**
	 * Method used to get the instance of {@link CategoryManager} stored in the class.
	 *
	 * @return The {@link CategoryManager} object
	 */
	public static CategoryManager getManager() {
		return CATEGORY_MANAGER;
	}
}
