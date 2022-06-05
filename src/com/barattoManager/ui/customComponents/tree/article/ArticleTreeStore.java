package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;
import com.barattoManager.ui.customComponents.menu.popup.TreePopup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class ArticleTreeStore extends ArticleTree {

	public ArticleTreeStore(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		super(dimension, usernameFilter, stateFilter);

		final TreePopup treePopup = new TreePopup(this);
		getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (SwingUtilities.isRightMouseButton(e)) {
					treePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	protected void createMeetNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getUuid());

		articleNode.add(generateFields(article));

		fatherNode.add(articleNode);
	}
}
