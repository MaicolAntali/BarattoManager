package com.barattoManager.ui.mvc.tree;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;

public class TreeUtils {

	public static ArrayList<String> treeNodeArrayToArrayList(TreeNode[] treeNodes, String separator) {
		var arrayList = new ArrayList<String>();
		for (TreeNode node : treeNodes) {
			arrayList.add(node.toString().split(separator)[0].trim());
		}
		return arrayList;
	}

}
