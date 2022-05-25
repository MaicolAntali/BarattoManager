package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.utils.History;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleTree extends JPanel {

	/**
	 * Icon for open category
	 */
	private static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	/**
	 * Icon for close category
	 */
	private static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	/**
	 * Icon for field
	 */
	private static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";

	public ArticleTree(Dimension dimension, String filter) {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Articoli");

		var nodeMap = new HashMap<String, HashMap<String, DefaultMutableTreeNode>>() {{
			put("Offerte Cancellate", new HashMap<>());
			put("Offerte Aperte", new HashMap<>());
		}};

		CategoryManager categoryManager = CategoryManager.getInstance();

		Collection<Article> articleCollection;
		if (filter.equals("*")) {
			articleCollection = ArticleManager.getInstance().getArticleMap().values();
		}
		else {
			articleCollection = ArticleManager.getInstance().getArticleMap().values().stream()
					.filter(article -> Objects.equals(article.getUserNameOwner().toLowerCase(), filter.toLowerCase()))
					.collect(Collectors.toList());
		}

		// Create all meet
		for (Article article : articleCollection) {
			var category = categoryManager.getCategoryByUuid(article.getCategoryUuid());
			if (category.isPresent()) {
				if (article.getArticleState() == Article.State.CANCELLED_OFFERT) {
					if (nodeMap.get("Offerte Cancellate").containsKey(category.get().getName())) {
						createMeetNode(article, nodeMap.get("Offerte Cancellate").get(category.get().getName()));
					}
					else {
						nodeMap.get("Offerte Cancellate").put(category.get().getName(), new DefaultMutableTreeNode(category.get().getName()));
						createMeetNode(article, nodeMap.get("Offerte Cancellate").get(category.get().getName()));

					}
				}
				else {
					if (nodeMap.get("Offerte Aperte").containsKey(category.get().getName())) {
						createMeetNode(article, nodeMap.get("Offerte Aperte").get(category.get().getName()));
					}
					else {
						nodeMap.get("Offerte Aperte").put(category.get().getName(), new DefaultMutableTreeNode(category.get().getName()));
						createMeetNode(article, nodeMap.get("Offerte Aperte").get(category.get().getName()));

					}
				}
			}
		}


		// add city to root node
		for (Map.Entry<String, HashMap<String, DefaultMutableTreeNode>> entry : nodeMap.entrySet()) {
			var stateNode = new DefaultMutableTreeNode(entry.getKey());
			for (DefaultMutableTreeNode node : entry.getValue().values()) {
				stateNode.add(node);
			}
			rootNode.add(stateNode);
		}

		var tree = new JTree(rootNode);

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));


		add(new JScrollPane(tree)).setPreferredSize(dimension);
		setVisible(true);
	}

	public ArticleTree(String filter ) {
		this(new Dimension(500, 290), filter);
	}

	private void createMeetNode(Article article, DefaultMutableTreeNode fatherNode) {
		// create the article node
		var articleNode = new DefaultMutableTreeNode(article.getUuid());

		// create owner node
		articleNode.add(new DefaultMutableTreeNode("Proprietario: %s".formatted(article.getUserNameOwner())));

		// create fields nodes
		var fieldsNode = new DefaultMutableTreeNode("Campi");
		for (Map.Entry<Field, String> entry : article.getFieldValueMap().entrySet()) {
			if (!entry.getValue().isBlank() || !entry.getKey().required()) {
				fieldsNode.add(new DefaultMutableTreeNode(("%s: %s").formatted(entry.getKey().name(), entry.getValue())));
			}
		}
		articleNode.add(fieldsNode);

		// create history nodes
		var historyNode = new DefaultMutableTreeNode("Log");
		for (History history : article.getHistory()) {
			historyNode.add(
					new DefaultMutableTreeNode("%s ~ %s - %s - %s".formatted(
					history.name().isPresent() ? "\u2705" : "\u274C",
					history.dateTime(),
					history.name().isPresent() ? history.name().get() : history.error().get(),
					history.description().get()
			)));
		}
		articleNode.add(historyNode);

		// add meetNode to father
		fatherNode.add(articleNode);
	}
}
