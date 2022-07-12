package com.barattoManager.ui.components.configurator;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.manager.factory.CategoryManagerFactory;
import com.barattoManager.model.category.Category;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.components.InitialMenuUI;
import com.barattoManager.ui.customComponents.buttons.CategoryConfButtons;
import com.barattoManager.ui.customComponents.tree.category.CategoryTree;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a JPanel that represent the category editor view (only configurator)
 */
public class ConfiguratorCategoryEditorUi extends JPanel implements DataChangeListener<String, Category> {

	/**
	 * {@link CategoryTree} object
	 */
	private CategoryTree categoryTree = new CategoryTree(
			CategoryManagerFactory.getManager()
					.getRootCategoryMap()
					.values()
					.stream()
					.toList()
	);
	/**
	 * {@link CategoryConfButtons} object
	 */
	private CategoryConfButtons categoryConfButtons = new CategoryConfButtons(categoryTree);
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
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		// add the tree and the buttons
		centerPanel.add(categoryTree);
		centerPanel.add(categoryConfButtons, BorderLayout.SOUTH);


		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

	@Override
	public void update(ConcurrentHashMap<String, Category> updatedMap) {
		centerPanel.remove(categoryTree);
		centerPanel.remove(categoryConfButtons);

		categoryTree = new CategoryTree(updatedMap.values().stream().toList());
		categoryConfButtons = new CategoryConfButtons(categoryTree);

		centerPanel.add(categoryTree);
		centerPanel.add(categoryConfButtons, BorderLayout.SOUTH);

		centerPanel.revalidate();
		centerPanel.repaint();
	}
}
