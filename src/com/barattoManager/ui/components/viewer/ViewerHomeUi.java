package com.barattoManager.ui.components.viewer;

import com.barattoManager.ui.components.ComponentsName;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that shows the viewer's home page
 */
public class ViewerHomeUi extends JPanel {

	private JPanel mainPanel;
	private JButton viewCategoryButton;
	private JButton viewMeetButton;
	private JButton yoursArticleButton;
	private JButton storeArticleButton;
	private JButton myTradesButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public ViewerHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		viewCategoryButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_CATEGORY.toString()));
		viewMeetButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_MEET.toString()));
		yoursArticleButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_YOUR_ARTICLES.toString()));
		storeArticleButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_STORE_ARTICLES.toString()));
		myTradesButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_EXCHANGES.toString()));
	}
}
