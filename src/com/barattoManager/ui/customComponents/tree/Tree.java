package com.barattoManager.ui.customComponents.tree;

import com.barattoManager.exception.NoNodeSelected;
import com.barattoManager.model.article.Article;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Objects;

/**
 * Abstract class that handles all tree creation and provides common utility methods
 *
 * @see JTree
 */
public abstract class Tree extends JPanel {

	protected static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	protected static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	protected static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";
	private static final String ERROR_NO_NODE_HAS_BEEN_SELECTED = "Non è stata selezionata nessun nodo. Seleziona un nodo e riprova.";

	private final JTree tree;

	/**
	 * Constructor of the class
	 *
	 * @param dimension {@link Dimension} of the {@link JPanel} that contains the tree
	 */
	public Tree(Dimension dimension) {
		this.tree = new JTree(getRootNode());

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));

		add(new JScrollPane(tree)).setPreferredSize(dimension);
		setVisible(true);
	}

	protected abstract DefaultMutableTreeNode getRootNode();

	/**
	 * Method used to get the {@link JTree} instance stored in the class
	 *
	 * @return {@link JTree} object
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Method that returns the node selected on the tree by the user.<br/>
	 * If the user does not select any node, an exception is thrown.
	 *
	 * @return {@link TreeNode} array which contains the path of the nodes
	 * @throws NoNodeSelected Is thrown if no node is selected
	 */
	public TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();

		if (selectedNode == null)
			throw new NoNodeSelected(ERROR_NO_NODE_HAS_BEEN_SELECTED);

		return selectedNode.getPath();
	}

	/**
	 * Method used to generate a node that contains all fields
	 *
	 * @param article {@link Article} object
	 * @return {@link DefaultMutableTreeNode} that contains all fields
	 */
	public DefaultMutableTreeNode generateFields(Article article) {
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
