package com.barattoManager.ui.customComponents.tree.article;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.utils.History;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class used to create a JPanel that contain a JTree
 */
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
	/**
	 * No node has been selected error
	 */
	private static final String ERROR_NO_NODE_HAS_BEEN_SELECTED = "Non è stato selezionato nessun nodo.";

	/**
	 * {@link JTree} object
	 */
	private final JTree tree;

	/**
	 * {@link ArticleTree} constructor
	 *
	 * @param dimension Dimension of the JPanel.
	 * @param usernameFilter Username filter
	 * @param stateFilter State filter
	 */
	public ArticleTree(Dimension dimension, String usernameFilter, Article.State stateFilter) {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Articoli");

		var nodeMap = new HashMap<String, HashMap<String, DefaultMutableTreeNode>>();

		CategoryManager categoryManager = CategoryManager.getInstance();

		Collection<Article> articleList;

		if (Objects.equals(usernameFilter, "*") && stateFilter == null)
			articleList = ArticleManager.getInstance().getArticleMap().values();
		else {
			if (!Objects.equals(usernameFilter, "*"))
				articleList = ArticleManager.getInstance().getArticles(usernameFilter);
			else
				articleList = ArticleManager.getInstance().getArticles(stateFilter);
		}


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

		// add city to root node
		nodeMap.forEach((key, value) -> {
			var stateNode = new DefaultMutableTreeNode(key);
			value.values().forEach(stateNode::add);
			rootNode.add(stateNode);
		});

		tree = new JTree(rootNode);

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));


		add(new JScrollPane(tree)).setPreferredSize(dimension);
		setVisible(true);
	}

	/**
	 * {@link ArticleTree} constructor
	 *
	 * @param usernameFilter Username filter
	 * @param stateFilter State filter
	 */
	public ArticleTree(String usernameFilter, Article.State stateFilter) {
		this(new Dimension(500, 290), usernameFilter, stateFilter);
	}

	/**
	 * Method used to get the current selected node in the {@link #tree}.
	 * @return Array of {@link TreeNode} that contains the node path.
	 * @throws NoNodeSelected is thrown if the user does not select any node.
	 */
	public TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedNode == null) {
			throw new NoNodeSelected(ERROR_NO_NODE_HAS_BEEN_SELECTED);
		}
		else {
			return selectedNode.getPath();
		}
	}

	/**
	 * Method used to create new node
	 * @param article {@link Article} want to create the node.
	 * @param fatherNode {@link DefaultMutableTreeNode} node to attach the new node
	 */
	private void createNode(Article article, DefaultMutableTreeNode fatherNode) {
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
					history.name().isPresent() ? history.name().get() : history.error().orElseThrow(NullPointerException::new),
					history.description().orElseThrow(NullPointerException::new)
			)));
		}
		articleNode.add(historyNode);

		// add meetNode to father
		fatherNode.add(articleNode);
	}
}
