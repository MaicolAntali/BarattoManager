package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.tree.TreeModel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StoreTreeModel extends TreeModel<Article> {

	public StoreTreeModel(List<Article> articles) {
		super(filterList(articles));
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		setData(filterList(updatedMap.values().stream().toList()));
		fireModelDataHasChangeListener();
	}


	private static List<Article> filterList(List<Article> articles) {
		return articles.parallelStream()
				.filter(article -> article.getArticleState() == Article.State.OPEN_OFFER)
				.filter(article -> !article.getUserNameOwner().equalsIgnoreCase(Store.getLoggedUser().getUsername()))
				.toList();
	}
}
