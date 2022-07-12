package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.manager.CategoryManager;
import com.barattoManager.manager.factory.CategoryManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class that represent the Article tree
 */
public abstract class ArticleTree extends Tree {


	public ArticleTree(List<Article> articles, Dimension dimension) {
		super(dimension);

		var nodeMap = generateNodeMap(articles);

		// add city to root node
		nodeMap.forEach((key, value) -> {
			var stateNode = new DefaultMutableTreeNode(key);
			value.values().forEach(stateNode::add);
			getRootNode().add(stateNode);
		});

		getTree().expandPath(new TreePath(getRootNode()));
	}


	public ArticleTree(List<Article> articles) {
		this(articles, new Dimension(500, 290));
	}


	abstract void createNode(Article article, DefaultMutableTreeNode fatherNode);

	private HashMap<String, HashMap<String, DefaultMutableTreeNode>> generateNodeMap(List<Article> articles) {
		CategoryManager categoryManager = CategoryManagerFactory.getManager();
		var             nodeMap         = new HashMap<String, HashMap<String, DefaultMutableTreeNode>>();

		articles
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
