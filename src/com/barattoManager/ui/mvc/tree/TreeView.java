package com.barattoManager.ui.mvc.tree;

import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public abstract class TreeView<T> implements View {

	private static final String ICON_CLOSE = "/icon/category_open.png";
	private static final String ICON_OPEN = "/icon/category_close.png";
	private static final String ICON_LEAF = "/icon/category_field.png";

	private final JPanel mainPanel;

	@TreeNodeSelectedListenerField
	private JTree tree;
	private DefaultMutableTreeNode rootNode;

	protected TreeView() {
		mainPanel = new JPanel();
	}

	protected abstract void drawNodes(List<T> list);

	protected abstract String getRootNodeName();

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void drawTree(List<T> list) {
		if (tree != null)
			mainPanel.removeAll();

		rootNode = new DefaultMutableTreeNode(getRootNodeName());

		drawNodes(list);

		tree = new JTree(rootNode);

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CLOSE))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_OPEN))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_LEAF))));

		mainPanel.add(new JScrollPane(tree)).setPreferredSize(new Dimension(500, 310));
		mainPanel.repaint();
		mainPanel.revalidate();
	}

	public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}

	public JTree getTree() {
		return tree;
	}
}
