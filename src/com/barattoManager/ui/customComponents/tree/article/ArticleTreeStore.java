package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.model.article.Article;
import com.barattoManager.utils.TreeUtils;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Class that represent the article tree in the store of the viewer
 */
public final class ArticleTreeStore extends ArticleTree {

	private DefaultMutableTreeNode rootNode;

	/**
	 * {@link ArticleTreeStore} constructor
	 * @param dimension {@link Dimension}
	 * @param usernameFilter Username filter
	 * @param stateFilter State filter
	 */
	public ArticleTreeStore(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension, usernameFilter, stateFilter);
	}

	@Override
	protected void createNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getArticleName());

		articleNode.add(new DefaultMutableTreeNode("UUID: %s".formatted(article.getUuid())));
		articleNode.add(TreeUtils.generateFields(article));

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
