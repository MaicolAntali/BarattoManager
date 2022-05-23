package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.category.CategoryConfButtons;
import com.barattoManager.ui.customComponents.tree.category.CategoryTree;
import com.barattoManager.ui.customComponents.tree.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.event.RepaintListener;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the category editor view (only configurator)
 */
public class ConfiguratorCategoryEditorUi extends JPanel implements RepaintListener {

	/**
	 *Event Handler for reprint the category tree and buttons
	 */
	private final RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
	/**
	 * {@link CategoryTree} object
	 */
	private CategoryTree categoryTree = new CategoryTree();
	/**
	 * {@link CategoryConfButtons} object
	 */
	private CategoryConfButtons categoryConfButtons = new CategoryConfButtons(categoryTree, repaintEventHandler);
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * Center JPanel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;
	/**
	 * Back button to {@link InitialMenuUI}
	 */
	private JButton backToInitButton;

	/**
	 * {@link ConfiguratorCategoryEditorUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		// Set up the repainting event
		repaintEventHandler.addListener(this);

		// add the tree and the buttons
		centerPanel.add(categoryTree);
		centerPanel.add(categoryConfButtons, BorderLayout.SOUTH);


		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

	/**
	 * Implementation of the method {@link RepaintListener#repaintCategoryTree()}
	 * Is used to repaint the Category Tree
	 */
	@Override
	public void repaintCategoryTree() {
		// remove components
		centerPanel.remove(categoryTree);
		centerPanel.remove(categoryConfButtons);

		// new instances
		categoryTree = new CategoryTree();
		categoryConfButtons = new CategoryConfButtons(categoryTree, repaintEventHandler);

		// add new component
		centerPanel.add(categoryTree);
		centerPanel.add(categoryConfButtons, BorderLayout.SOUTH);

		// Revalidate & Repaint
		centerPanel.revalidate();
		centerPanel.repaint();
	}

}
