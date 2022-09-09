package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.ui.mvc.tree.TreeModel;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model of {@link CategoryTreeController} that contains the data
 */
public class CategoryTreeModel extends TreeModel<Category> {

	/**
	 * Constructor of the class
	 * @param categories {@link List}
	 */
	public CategoryTreeModel(List<Category> categories) {
		super(categories);
	}

	@Override
	public void update(ConcurrentHashMap<String, Category> updatedMap) {
		setData(updatedMap.values().stream().toList());
		fireModelDataHasChangeListener();
	}
}
