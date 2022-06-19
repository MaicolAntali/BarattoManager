package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.CategoryManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.field.Field;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ArticleTree extends Tree {

	private final String username;

	/**
	 * {@link ArticleTree} constructor
	 *
	 * @param dimension      Dimension of the JPanel.
	 * @param usernameFilter Username filter
	 * @param stateFilter    State filter
	 */
	public ArticleTree(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension);
		this.username = usernameFilter.replace("!", "");

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

	public String getUsername() {
		return username;
	}

	protected DefaultMutableTreeNode generateFields(Article article) {
		var fieldsNode = new DefaultMutableTreeNode("Campi");

		for (Map.Entry<Field, String> entry : article.getFieldValueMap().entrySet()) {
			if (!entry.getValue().isBlank() || !entry.getKey().required()) {
				fieldsNode.add(new DefaultMutableTreeNode(("%s: %s").formatted(entry.getKey().name(), entry.getValue())));
			}
		}

		return fieldsNode;
	}


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
