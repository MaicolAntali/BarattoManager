package com.barattoManager.ui.panels.categoryTree;

import javax.swing.*;
import java.awt.*;

public class TreeContainer extends JPanel implements RepaintingEventHandler{

	private TreeView treeView = new TreeView();
	private TreeActionButtons treeActionButtons = new TreeActionButtons(treeView);
	public TreeContainer() {
		add(treeView);
		add(treeActionButtons, BorderLayout.SOUTH);
	}

	@Override
	public void repaintTreeContainerComponents() {
		// remove components
		remove(treeView);
		remove(treeActionButtons);

		// new instances
		treeView = new TreeView();
		treeActionButtons = new TreeActionButtons(treeView);

		// add new component
		add(treeView);
		add(treeActionButtons, BorderLayout.SOUTH);

		// Revalidate & Repaint
		revalidate();
		repaint();
	}
}
