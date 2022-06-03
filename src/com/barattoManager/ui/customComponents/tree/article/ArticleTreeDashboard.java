package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;
import com.barattoManager.utils.History;

import javax.swing.tree.DefaultMutableTreeNode;

public final class ArticleTreeDashboard extends ArticleTree {

	public ArticleTreeDashboard(String usernameFilter, Article.State stateFilter) {
		super(usernameFilter, stateFilter);
	}

	@Override
	protected void createMeetNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getUuid());

		articleNode.add(new DefaultMutableTreeNode("Proprietario: %s".formatted(article.getUserNameOwner())));

		articleNode.add(generateFields(article));

		var historyNode = new DefaultMutableTreeNode("Log");
		for (History history : article.getHistory()) {
			historyNode.add(
					new DefaultMutableTreeNode("%s ~ %s - %s - %s".formatted(
							history.name().isPresent() ? "\u2705" : "\u274C",
							history.dateTime(),
							history.name().isPresent() ? history.name().get() : history.error().orElseThrow(NullPointerException::new),
							history.description().orElseThrow(NullPointerException::new)
					)));
		}
		articleNode.add(historyNode);

		fatherNode.add(articleNode);
	}
}
