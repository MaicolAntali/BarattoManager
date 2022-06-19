package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.model.article.Article;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public final class ArticleTreeStore extends ArticleTree {

	private DefaultMutableTreeNode rootNode;

	public ArticleTreeStore(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension, usernameFilter, stateFilter);
	}

	@Override
	protected void createNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getArticleName());

		articleNode.add(new DefaultMutableTreeNode("UUID: %s".formatted(article.getUuid())));
		articleNode.add(generateFields(article));

		fatherNode.add(articleNode);

		getTree().expandPath(new TreePath(getRootNode()));
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Categorie");

		return rootNode;
	}
}
