package com.barattoManager.ui.customComponents.tree;

import com.barattoManager.exception.NoNodeSelected;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Objects;

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

	private final JTree tree;

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

	public JTree getTree() {
		return tree;
	}

	public TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();

		if (selectedNode == null)
			throw new NoNodeSelected("Non è stata selezionata nessun nodo. Seleziona un nodo e riprova.");

		return selectedNode.getPath();
	}
}
