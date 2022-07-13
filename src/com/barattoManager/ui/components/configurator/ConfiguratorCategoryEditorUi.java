package com.barattoManager.ui.components.configurator;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.manager.factory.CategoryManagerFactory;
import com.barattoManager.model.category.Category;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.buttons.CategoryConfButtons;
import com.barattoManager.ui.customComponents.tree.category.CategoryTree;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the configurator to manage the {@link Category categories}
 */
public class ConfiguratorCategoryEditorUi extends JPanel implements DataChangeListener<String, Category> {

	private CategoryTree categoryTree = new CategoryTree(
			CategoryManagerFactory.getManager()
					.getRootCategoryMap()
					.values()
					.stream()
					.toList()
	);
	private CategoryConfButtons categoryConfButtons = new CategoryConfButtons(categoryTree);
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToInitButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

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
