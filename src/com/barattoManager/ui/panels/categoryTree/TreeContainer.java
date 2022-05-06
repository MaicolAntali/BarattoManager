package com.barattoManager.ui.panels.categoryTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to contain {@link TreeView} and {@link TreeActionButtons}
 */
public class TreeContainer extends JPanel implements RepaintingEventHandler{

	/**
	 * {@link TreeView} object
	 */
	private TreeView treeView = new TreeView();
	/**
	 * {@link TreeActionButtons} obejct
	 */
	private TreeActionButtons treeActionButtons = new TreeActionButtons(treeView);

	/**
	 * {@link TreeContainer} constructor
	 */
	public TreeContainer() {
		add(treeView);
		add(treeActionButtons, BorderLayout.SOUTH);
	}

	/**
	 * Implementation of the method {@link RepaintingEventHandler#repaintTreeContainerComponents()}
	 */
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
