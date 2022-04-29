package com.barattoManager.ui.panels.categoryTree;

import javax.swing.*;
import java.awt.*;

public class TreeContainer extends JPanel {
	public TreeContainer() {
		var treeView = new TreeView();
		add(treeView);
		add(new TreeActionButtons(treeView), BorderLayout.SOUTH);
	}

}
