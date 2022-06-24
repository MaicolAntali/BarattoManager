package com.barattoManager.utils;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.category.field.Field;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Map;

/**
 * Utility class of tree
 */
public final class TreeUtils {
	/**
	 * Method to convert an array of {@link TreeNode} in to an array of {@link String}
	 * @param nodePath Array of {@link TreeNode} to convert
	 * @return Array of {@link String}
	 */
	public static ArrayList<String> treeNodeArrayToArrayList(TreeNode[] nodePath) {
		var arrayList = new ArrayList<String>();
		for (TreeNode node: nodePath) {
			// Split the category name and description and take only the category name
			arrayList.add(node.toString().split("~")[0].trim());
		}
		return arrayList;
	}

	public static DefaultMutableTreeNode generateFields(Article article) {
		var fieldsNode = new DefaultMutableTreeNode("Campi");

		for (Map.Entry<Field, String> entry : article.getFieldValueMap().entrySet()) {
			if (!entry.getValue().isBlank() || !entry.getKey().required()) {
				fieldsNode.add(new DefaultMutableTreeNode(("%s: %s").formatted(entry.getKey().name(), entry.getValue())));
			}
		}

		return fieldsNode;
	}
}
