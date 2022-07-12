package com.barattoManager.ui.customComponents.tree.category;

import com.barattoManager.model.category.Category;
import com.barattoManager.model.category.field.Field;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * Class used to create a JPanel that contain a JTree
 */
public class CategoryTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	public CategoryTree(List<Category> categories, Dimension dimension) {
		super(dimension);

		categories.forEach(category -> createSubCategoryNode(category, createNode(category, rootNode)));

		getTree().expandPath(new TreePath(getRootNode()));
	}

	public CategoryTree(List<Category> categories) {
		this(categories, new Dimension(500, 290));
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Categorie");

		return rootNode;
	}


	/**
	 * Method used to create a complete node (category + field).
	 *
	 * @param category   {@link Category} want to create the node.
	 * @param fatherNode {@link DefaultMutableTreeNode} node to attach the new category node
	 * @return {@link DefaultMutableTreeNode} new category node
	 */
	private DefaultMutableTreeNode createNode(Category category, DefaultMutableTreeNode fatherNode) {
		// Add the category
		var node = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		fatherNode.add(node);

		// Add fields
		for (Field categoryField : category.getCategoryFields().values()) {
			node.add(new DefaultMutableTreeNode(("%s: %s").formatted(categoryField.name(), categoryField.required())));
		}
		return node;
	}


	/**
	 * Recursive method used to append the sub category
	 *
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
}
