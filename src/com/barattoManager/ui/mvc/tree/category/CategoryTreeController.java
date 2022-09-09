package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.CategoryUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

/**
 * Controller that handle the events of the {@link CategoryTreeView} and update the data in the {@link CategoryTreeModel}
 */
public class CategoryTreeController extends TreeController<Category> implements ModelDataHasChangeListener {

	/**
	 * Constructor of the class
	 *
	 * @param model {@link CategoryTreeModel} represent the model of the controller
	 * @param view  {@link  CategoryTreeView} represent the view of the controller
	 */
	public CategoryTreeController(CategoryTreeModel model, CategoryTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		CategoryUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
