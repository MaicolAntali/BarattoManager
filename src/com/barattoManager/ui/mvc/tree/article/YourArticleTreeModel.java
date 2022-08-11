package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.Store;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.event.UpdateDataListener;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class YourArticleTreeModel implements ArticleTreeModel, UpdateDataListener<String, Article> {

	private final ArrayList<ModelDataHasChangeListener> listeners;
	private List<Article> articles;
	private TreeNode[] treeNodes;

	public YourArticleTreeModel(List<Article> articles) {
		this.articles = filterList(articles);

		listeners = new ArrayList<>();
		treeNodes = null;
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		this.articles = filterList(updatedMap.values().stream().toList());
		fireModelDataHasChangeListener();
	}


	@Override
	public List<Article> getArticles() {
		return articles;
	}

	@Override
	public TreeNode[] getTreeNodes() {
		return treeNodes;
	}

	@Override
	public void setTreeNodes(TreeNode[] treeNodes) {
		this.treeNodes = treeNodes;
	}

	@Override
	public void addModelDataHasChangeListener(ModelDataHasChangeListener listener) {
		this.listeners.add(listener);
	}

	private void fireModelDataHasChangeListener() {
		this.listeners.forEach(ModelDataHasChangeListener::dataChange);
	}

	private List<Article> filterList(List<Article> articles) {
		return articles.stream()
				.filter(article -> article.getUserNameOwner().equalsIgnoreCase(Store.getLoggedUser().getUsername()))
				.toList();
	}
}
