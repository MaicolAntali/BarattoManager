package com.barattoManager.ui.panels.categoryTree;

import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.NoNodeSelected;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.Objects;

public class TreeView extends JPanel {

	private static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	private static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	private static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";

	private final JTree tree;

	public TreeView() {
		// Populate the tree with category
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Categorie");
		for (Category cat : CategoryManager.getInstance().getCategoryMap().values()) {
			var node = createNode(cat, rootNode);
			createSubCategoryNode(cat, node);
		}

		// Create the tree based on rootNodeNode
		tree = new JTree(rootNode);

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));


		add(new JScrollPane(tree)).setPreferredSize(new Dimension(500, 290));
		setVisible(true);
	}

	/**
	 * Method used to create a complete node (category + field).
	 *
	 * @param category   {@link Category} want to create the node.
	 * @param fatherNode {@link DefaultMutableTreeNode} node to attach the new category node
	 * @return {@link DefaultMutableTreeNode} New category node
	 */
	private DefaultMutableTreeNode createNode(Category category, DefaultMutableTreeNode fatherNode) {
		// Add the category
		var node = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		fatherNode.add(node);

		// Add fields
		for (Field categoryField : category.getCategoryFields().values()) {
			node.add(new DefaultMutableTreeNode(("%s: %s").formatted(categoryField.getName(), categoryField.isRequired())));
		}
		return node;
	}

	/**
	 * Recursive method used to append the sub category
	 * @param cat    Category that want append in the tree
	 * @param catAdd Node of the tree where the category will be appended.
	 */
	private void createSubCategoryNode(Category cat, DefaultMutableTreeNode catAdd) {
		if (!cat.getSubCategory().isEmpty()) {
			for (Category subCat : cat.getSubCategory().values()) {
				DefaultMutableTreeNode node = createNode(subCat, catAdd);

				createSubCategoryNode(subCat, node);
			}
		}
	}

	/**
	 * Method used to get the current selected node in the {@link #tree}.
	 * @return Array of {@link TreeNode} that contains the node path.
	 * @throws NoNodeSelected is thrown if the user does not select any node.
	 */
	public TreeNode[] getSelectedPathNode() throws NoNodeSelected {
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedNode == null) {
			throw new NoNodeSelected("Non è stato selezionata nessuna categoria. Seleziona una categoria e riprova.");
		}
		else {
			return selectedNode.getPath();
		}
	}
}
