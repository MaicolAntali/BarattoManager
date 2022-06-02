package com.barattoManager.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the Viewer home view
 */
public class ViewerHomeUi extends JPanel {
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * CategoryView button to {@link ViewerCategoryViewUi}
	 */
	private JButton viewCategoryButton;
	/**
	 * MeetView button to {@link ViewerMeetViewUi}
	 */
	private JButton viewMeetButton;
	private JButton yoursArticleButton;
	private JButton storeArticleButton;

	/**
	 * {@link ViewerHomeUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
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
	}
}
