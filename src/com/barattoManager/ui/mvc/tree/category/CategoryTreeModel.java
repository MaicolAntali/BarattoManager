package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.ui.mvc.tree.TreeModel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryTreeModel extends TreeModel<Category> {

	public CategoryTreeModel(List<Category> categories) {
		super(categories);
	}

	@Override
	public void update(ConcurrentHashMap<String, Category> updatedMap) {
		setData(updatedMap.values().stream().toList());
		fireModelDataHasChangeListener();
	}
}
