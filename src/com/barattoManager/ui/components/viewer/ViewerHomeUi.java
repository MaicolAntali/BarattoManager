package com.barattoManager.ui.components.viewer;

import com.barattoManager.ui.components.ComponentsName;

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
	/**
	 * YoursArticle button to {@link ViewerDashboardArticle}
	 */
	private JButton yoursArticleButton;
	/**
	 * ArticleStore button to {@link ViewerStoreArticle}
	 */
	private JButton storeArticleButton;
	private JButton iTuoiScambiButton;

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
		iTuoiScambiButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_EXCHANGES.toString()));
	}
}
