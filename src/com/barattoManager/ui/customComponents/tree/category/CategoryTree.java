package com.barattoManager.ui.customComponents.tree.category;

import com.barattoManager.model.category.Category;
import com.barattoManager.model.category.field.Field;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * Class used to create the {@link Category} tree
 */
public class CategoryTree extends Tree {

	private DefaultMutableTreeNode rootNode;

	/**
	 * Constructor of the class
	 *
	 * @param categories {@link List} that contains the {@link Category categories}
	 * @param dimension  {@link Dimension} of the {@link JPanel} that contains the tree
	 */
	public CategoryTree(List<Category> categories, Dimension dimension) {
		super(dimension);

		categories.forEach(category -> createSubCategoryNode(category, createNode(category, rootNode)));

		getTree().expandPath(new TreePath(getRootNode()));
	}

	/**
	 * Constructor of the class
	 *
	 * @param categories {@link List} that contains the {@link Category categories}
	 */
	public CategoryTree(List<Category> categories) {
		this(categories, new Dimension(500, 290));
	}

	@Override
	protected DefaultMutableTreeNode getRootNode() {
		if (rootNode == null)
			rootNode = new DefaultMutableTreeNode("Categorie");

		return rootNode;
	}

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

	private void createSubCategoryNode(Category cat, DefaultMutableTreeNode catAdd) {
		if (!cat.getSubCategory().isEmpty()) {
			for (Category subCat : cat.getSubCategory().values()) {
				DefaultMutableTreeNode node = createNode(subCat, catAdd);

				createSubCategoryNode(subCat, node);
			}
		}
	}
}
