package com.barattoManager.ui.panels.categoryTree;

import com.barattoManager.category.Category;
import com.barattoManager.category.CategoryManager;
import com.barattoManager.category.field.Field;
import com.barattoManager.exception.NoNodeSelected;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TreeView extends JPanel {

	private static final String ICON_CATEGORY_OPEN = "/icon/category_open.png";
	private static final String ICON_CATEGORY_CLOSE = "/icon/category_close.png";
	private static final String ICON_CATEGORY_FIELD = "/icon/category_field.png";

	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Categorie");
	private final JTree tree;
	private final DefaultTreeModel treeModel;

	public TreeView() {
		// Populate the tree with category
		for (Category cat : CategoryManager.getInstance().getCategoryMap().values()) {
			var node = createNode(cat, rootNode);
			createSubCategoryNode(cat, node);
		}

		// Create the tree based on rootNodeNode
		tree = new JTree(rootNode);

		// Set the tree model
		treeModel = (DefaultTreeModel) tree.getModel();

		// Change the default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setClosedIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_OPEN))));
		renderer.setOpenIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_CLOSE))));
		renderer.setLeafIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource(ICON_CATEGORY_FIELD))));


		add(new JScrollPane(tree)).setPreferredSize(new Dimension(500, 290));
		setVisible(true);
	}

	/**
	 * Method used to create a complete (category + field) node.
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
	 *
	 * @param cat    Category that want append in the tree
	 * @param catAdd Node of the tree where teh category will be appended.
	 */
	private void createSubCategoryNode(Category cat, DefaultMutableTreeNode catAdd) {
		if (!cat.getSubCategory().isEmpty()) {
			for (Category subCat : cat.getSubCategory().values()) {
				DefaultMutableTreeNode node = createNode(subCat, catAdd);

				createSubCategoryNode(subCat, node);
			}
		}
	}

	private void addFieldNodes(DefaultMutableTreeNode newFieldNode, DefaultMutableTreeNode node) {
		if (node.getChildCount() != 0) {
			for (int i = 0; i < node.getChildCount(); i++) {
				var subNode = node.getChildAt(i);
				if (!subNode.isLeaf()) {
					insertNewNodeAndUpdate(newFieldNode, (DefaultMutableTreeNode) subNode);
				}
				addFieldNodes(newFieldNode, (DefaultMutableTreeNode) subNode);
			}
		}
		else {
			if (!node.isLeaf()) {
				insertNewNodeAndUpdate(newFieldNode, node);
			}
		}
	}

	/**
	 * Method used to insert a new node on the fly and update the view of tree.
	 * @param nodeToAdd Node to add in the {@link #tree}
	 * @param fatherNode Node that's the father of new node.
	 */
	private void insertNewNodeAndUpdate(DefaultMutableTreeNode nodeToAdd, DefaultMutableTreeNode fatherNode) {
		treeModel.insertNodeInto(nodeToAdd, fatherNode, fatherNode.getChildCount());
		treeModel.reload(nodeToAdd);
		TreeNode[] CategoryNodes = treeModel.getPathToRoot(nodeToAdd);
		var CategoryPath = new TreePath(CategoryNodes);
		tree.scrollPathToVisible(CategoryPath);
	}

	/**
	 * Method used to paint in the {@link #tree} a new category (Category + Fields).
	 * @param category {@link Category} to paint in the {@link #tree}
	 */
	public void paintNewCategory(Category category) {
		// Print the new category
		var newCategoryNode = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		insertNewNodeAndUpdate(newCategoryNode, selectedNode);

		// Print the Fields
		for(Field field: category.getCategoryFields().values()) {
			var newFieldNode = new DefaultMutableTreeNode("%s: %s".formatted(field.getName(), field.isRequired()));
			insertNewNodeAndUpdate(newFieldNode, newCategoryNode);
		}
	}

	/**
	 * Method used to paint in the {@link #tree} a new field.
	 * @param category {@link Category} to start adding the new field
	 * @param newFieldName Field Name
	 */
	public void paintNewField(Category category, String newFieldName) {

		var field = category.getCategoryFields().get(newFieldName.toLowerCase());

		var newFieldNode = new DefaultMutableTreeNode(("%s: %s").formatted(field.getName(), field.isRequired()));
		var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		insertNewNodeAndUpdate(newFieldNode, selectedNode);
		addFieldNodes(newFieldNode, selectedNode);
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
