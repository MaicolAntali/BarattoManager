package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.tree.TreeModel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConfiguratorTreeModel extends TreeModel<Article> {

	public ConfiguratorTreeModel(List<Article> articles) {
		super(articles);
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		setData(updatedMap.values().stream().toList());
		fireModelDataHasChangeListener();
	}
}
