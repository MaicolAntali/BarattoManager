package com.barattoManager.ui.mvc.tree.category;

import com.barattoManager.services.category.Category;
import com.barattoManager.services.category.field.Field;
import com.barattoManager.ui.mvc.tree.TreeView;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class CategoryTreeView extends TreeView<Category> {

	public CategoryTreeView() {
		super();
	}

	@Override
	protected void drawNodes(List<Category> list) {
		list.forEach(
				category -> createCategoryStruct(category, createCategoryNode(category, getRootNode()))
		);
	}

	@Override
	protected String getRootNodeName() {
		return "Categorie";
	}


	private DefaultMutableTreeNode createCategoryNode(Category category, DefaultMutableTreeNode fatherNode) {
		var node = new DefaultMutableTreeNode(("%s ~ %s").formatted(category.getName(), category.getDescription()));
		fatherNode.add(node);

		for (Field categoryField : category.getCategoryFields().values()) {
			node.add(new DefaultMutableTreeNode(("%s: %s").formatted(categoryField.name(), categoryField.required())));
		}
		return node;
	}

	private void createCategoryStruct(Category cat, DefaultMutableTreeNode catAdd) {
		if (!cat.getSubCategory().isEmpty()) {
			for (Category subCat : cat.getSubCategory().values()) {
				DefaultMutableTreeNode node = createCategoryNode(subCat, catAdd);

				createCategoryStruct(subCat, node);
			}
		}
	}


}
