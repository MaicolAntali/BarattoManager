package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.utils.History;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.text.StyleConstants.setIcon;

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

		var nodeMap = new HashMap<String, DefaultMutableTreeNode>();

		CategoryManager categoryManager = CategoryManager.getInstance();

		Collection<Article> articleCollection = null;
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
				if (nodeMap.containsKey(category.get().getName())) {
					createMeetNode(article, nodeMap.get(category.get().getName()));
				}
				else {
					nodeMap.put(category.get().getName(), new DefaultMutableTreeNode(category.get().getName()));
					createMeetNode(article, nodeMap.get(category.get().getName()));
				}
			}
		}


		// add city to root node
		for (DefaultMutableTreeNode node : nodeMap.values()) {
			rootNode.add(node);
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

		// create state node
		articleNode.add(new DefaultMutableTreeNode("Stato: %s".formatted(article.getArticleState())));

		// create owner node
		articleNode.add(new DefaultMutableTreeNode("Proprietario: %s".formatted(article.getUserNameOwner())));


		// create fields nodes
		var fieldsNode = new DefaultMutableTreeNode("Campi");
		for (Map.Entry<Field, String> entry : article.getFieldValueMap().entrySet()) {
			if (!entry.getValue().isBlank()) {
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
