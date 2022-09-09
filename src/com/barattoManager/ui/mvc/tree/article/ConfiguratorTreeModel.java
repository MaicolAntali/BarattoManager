package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.tree.TreeModel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model of {@link ArticleTreeController} that contains the data
 */
public class ConfiguratorTreeModel extends TreeModel<Article> {

	/**
	 * Constructor of the class
	 *
	 * @param articles {@link List}
	 */
	public ConfiguratorTreeModel(List<Article> articles) {
		super(articles);
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		setData(updatedMap.values().stream().toList());
		fireModelDataHasChangeListener();
	}
}
