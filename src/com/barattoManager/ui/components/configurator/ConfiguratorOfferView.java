package com.barattoManager.ui.components.configurator;

import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that allows the configurator to see the offers
 */
public class ConfiguratorOfferView extends JPanel {

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
	public ConfiguratorOfferView(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new ArticleTreeStore(ArticleManagerFactory.getManager().getArticleMap().values().stream().toList()));

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}
}
