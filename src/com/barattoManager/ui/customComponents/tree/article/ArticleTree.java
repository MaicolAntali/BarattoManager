package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.CategoryManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public abstract class ArticleTree extends Tree {

	/**
	 * {@link ArticleTree} constructor
	 *
	 * @param dimension      Dimension of the JPanel.
	 * @param usernameFilter Username filter
	 * @param stateFilter    State filter
	 */
	public ArticleTree(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension);
		String username = usernameFilter.replace("!", "");

		var nodeMap = generateNodeMap(username, stateFilter);

		// add city to root node
		nodeMap.forEach((key, value) -> {
			var stateNode = new DefaultMutableTreeNode(key);
			value.values().forEach(stateNode::add);
			getRootNode().add(stateNode);
		});

		getTree().expandPath(new TreePath(getRootNode()));
	}

	/**
	 * {@link ArticleTree} constructor
	 *
	 * @param usernameFilter Username filter
	 * @param stateFilter    State filter
	 */
	public ArticleTree(String usernameFilter, Article.State stateFilter) {
		this(new Dimension(500, 290), usernameFilter, stateFilter);
	}


	abstract void createNode(Article article, DefaultMutableTreeNode fatherNode);

	private HashMap<String, HashMap<String, DefaultMutableTreeNode>> generateNodeMap(String usernameFilter, Article.State stateFilter) {
		CategoryManager categoryManager = CategoryManager.getInstance();
		var             nodeMap         = new HashMap<String, HashMap<String, DefaultMutableTreeNode>>();
		List<Article>   articleList;


		if (stateFilter == null)
			articleList = ArticleManager.getInstance().getArticlesByOwner(usernameFilter);
		else
			articleList = ArticleManager.getInstance().getArticlesByStatusExceptOwner(Article.State.OPEN_OFFER, usernameFilter.substring(1));


		articleList
				.forEach(article -> {
					var category = categoryManager.getCategoryByUuid(article.getCategoryUuid());

					if (!nodeMap.containsKey(article.getArticleState().toString())) {
						nodeMap.put(article.getArticleState().toString(), new HashMap<>());
					}

					if (nodeMap.get(article.getArticleState().toString()).containsKey(category.orElseThrow(NullPointerException::new).getName()))
						createNode(article, nodeMap.get(article.getArticleState().toString()).get(category.orElseThrow(NullPointerException::new).getName()));
					else {
						nodeMap.get(article.getArticleState().toString()).put(category.get().getName(), new DefaultMutableTreeNode(category.get().getName()));
						createNode(article, nodeMap.get(article.getArticleState().toString()).get(category.orElseThrow(NullPointerException::new).getName()));
					}
				});

		return nodeMap;
	}
}
