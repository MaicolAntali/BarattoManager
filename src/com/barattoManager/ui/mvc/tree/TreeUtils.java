package com.barattoManager.ui.mvc.tree;

import com.barattoManager.services.article.Article;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;

/**
 * Utility class used by tree
 */
public class TreeUtils {

	/**
	 * Method used to parse a {@link TreeNode} array into an {@link ArrayList}
	 * @param treeNodes {@link TreeNode} array
	 * @param separator {@link String} represent the line separator
	 * @return parsed arraylist
	 */
	public static ArrayList<String> treeNodeArrayToArrayList(TreeNode[] treeNodes, String separator) {
		var arrayList = new ArrayList<String>();
		for (TreeNode node : treeNodes) {
			arrayList.add(node.toString().split(separator)[0].trim());
		}
		return arrayList;
	}

	/**
	 * Method used to generate fields
	 * @param article {@link Article} object
	 * @return {@link DefaultMutableTreeNode} that contains all article fields
	 */
	public static DefaultMutableTreeNode generateFields(Article article) {
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
