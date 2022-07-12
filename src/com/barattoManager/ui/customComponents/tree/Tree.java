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
 * Abstract class used to generate tree
 */
public abstract class Tree extends JPanel {
	/**
	 * Icon for open category
	 */
	protected static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	/**
	 * Icon for close category
	 */
	protected static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	/**
	 * Icon for field
	 */
	protected static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";
	/**
	 * Error: No node has been selected, try again.
	 */
	private static final String ERROR_NO_NODE_HAS_BEEN_SELECTED = "Non è stata selezionata nessun nodo. Seleziona un nodo e riprova.";


	private final JTree tree;

	/**
	 * {@link Tree} constructor
	 *
	 * @param dimension {@link Dimension} object
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
	 * Method used to get the {@link JTree} object
	 *
	 * @return {@link JTree} object
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Method used to get the selected node path
	 *
	 * @return the selected node path
	 * @throws NoNodeSelected Is thrown if no node is selected
	 */
	public TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();

		if (selectedNode == null)
			throw new NoNodeSelected(ERROR_NO_NODE_HAS_BEEN_SELECTED);

		return selectedNode.getPath();
	}

	/**
	 * Method used to generate the node of all field in an article
	 *
	 * @param article {@link Article} article object
	 * @return {@link DefaultMutableTreeNode} Node that keep all fields
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
