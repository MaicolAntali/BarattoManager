package com.barattoManager.utils;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;

/**
 * Utility class containing common tree methods
 */
public final class TreeUtils {
	/**
	 * Method to convert an array of {@link TreeNode} in to an {@link ArrayList} of {@link String}
	 *
	 * @param nodePath Array of {@link TreeNode} to convert
	 * @return {@link ArrayList} of {@link String}
	 */
	public static ArrayList<String> treeNodeArrayToArrayList(TreeNode[] nodePath) {
		var arrayList = new ArrayList<String>();
		for (TreeNode node : nodePath) {
			// Split the category name and description and take only the category name
			arrayList.add(node.toString().split("~")[0].trim());
		}
		return arrayList;
	}
}
