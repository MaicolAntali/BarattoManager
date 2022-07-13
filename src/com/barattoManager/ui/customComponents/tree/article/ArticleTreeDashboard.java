package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.history.History;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

/**
 * Class used to create the {@link Article} tree
 */
public final class ArticleTreeDashboard extends ArticleTree {

	private DefaultMutableTreeNode rootNode;

	/**
	 * Constructor of the class
	 *
	 * @param articles {@link List} that contains the {@link Article}
	 */
	public ArticleTreeDashboard(List<Article> articles) {
		super(articles);
	}

	@Override
	protected void createNode(Article article, DefaultMutableTreeNode fatherNode) {
		var articleNode = new DefaultMutableTreeNode(article.getArticleName());

		articleNode.add(new DefaultMutableTreeNode("Proprietario: %s".formatted(article.getUserNameOwner())));
		articleNode.add(new DefaultMutableTreeNode("UUID: %s".formatted(article.getUuid())));

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

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Categorie");

		return rootNode;
	}
}
