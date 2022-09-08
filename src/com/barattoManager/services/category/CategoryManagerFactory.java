package com.barattoManager.services.category;

import com.barattoManager.services.article.ArticleManager;
import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;

/**
 * Class that constructs the {@link CategoryManager}<br/>
 * {@link CategoryManager}is declared in the class as a static field, to ensure one instance for the whole project.
 * It implements {@link Runnable} because it is run in a separated thread
 */
public class CategoryManagerFactory implements Runnable {

	private static CategoryManager CATEGORY_MANAGER;

	/**
	 * Method used to get the category manager
	 * @return CATEGORY_MANAGER {@link CategoryManager}
	 */
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
