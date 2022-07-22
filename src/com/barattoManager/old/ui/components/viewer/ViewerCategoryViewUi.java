package com.barattoManager.old.ui.components.viewer;

import com.barattoManager.old.manager.factory.CategoryManagerFactory;
import com.barattoManager.old.sample.category.Category;
import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.ui.customComponents.tree.category.CategoryTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that allows the Viewer to see the {@link Category categories}
 */
public class ViewerCategoryViewUi extends JPanel {

	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToHomeButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public ViewerCategoryViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new CategoryTree(
						CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList(),
						new Dimension(520, 330)
				)
		);

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
