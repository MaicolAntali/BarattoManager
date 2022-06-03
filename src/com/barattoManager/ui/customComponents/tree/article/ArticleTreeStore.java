package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class ArticleTreeStore extends ArticleTree {

	public ArticleTreeStore(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension, usernameFilter, stateFilter);
	}

	@Override
	protected void createMeetNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getUuid());

		articleNode.add(generateFields(article));

		fatherNode.add(articleNode);
	}
}
