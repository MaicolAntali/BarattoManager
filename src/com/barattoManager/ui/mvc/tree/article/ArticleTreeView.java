package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.services.history.History;
import com.barattoManager.ui.mvc.tree.TreeView;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.List;

public class ArticleTreeView extends TreeView<Article> {
	@Override
	protected void drawNodes(List<Article> list) {

		var nodeMap = new HashMap<String, HashMap<String, DefaultMutableTreeNode>>();

		list.forEach(
				article -> {

					var category = CategoryManagerFactory.getManager().getCategoryByUuid(article.getCategoryUuid());

					if (!nodeMap.containsKey(article.getArticleState().toString())) {
						nodeMap.put(article.getArticleState().toString(), new HashMap<>());
					}

					if (nodeMap.get(article.getArticleState().toString()).containsKey(category.orElseThrow(NullPointerException::new).getName()))
						createNode(article, nodeMap.get(article.getArticleState().toString()).get(category.orElseThrow(NullPointerException::new).getName()));
					else {
						nodeMap.get(article.getArticleState().toString()).put(category.get().getName(), new DefaultMutableTreeNode(category.get().getName()));
						createNode(article, nodeMap.get(article.getArticleState().toString()).get(category.orElseThrow(NullPointerException::new).getName()));
					}

				}
		);

		nodeMap.forEach((key, value) -> {
			var stateNode = new DefaultMutableTreeNode(key);

			value.values().forEach(stateNode::add);

			getRootNode().add(stateNode);
		});
	}

	@Override
	protected String getRootNodeName() {
		return "Articoli";
	}

	private void createNode(Article article, DefaultMutableTreeNode fatherNode) {
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

	private DefaultMutableTreeNode generateFields(Article article) {
		var fieldsNode = new DefaultMutableTreeNode("Campi");


		article.getFieldValueMap().forEach((key, value) -> {
			if (key.required())
				fieldsNode.add(new DefaultMutableTreeNode(("%s: %s").formatted(key.name(), value)));

			if (!key.required() && !value.isBlank())
				fieldsNode.add(new DefaultMutableTreeNode(("%s: %s").formatted(key.name(), value)));
		});

		return fieldsNode;
	}
}
