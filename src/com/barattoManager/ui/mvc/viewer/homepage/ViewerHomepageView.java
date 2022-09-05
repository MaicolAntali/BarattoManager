package com.barattoManager.ui.mvc.viewer.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public class ViewerHomepageView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton viewCategoryButton;
	@ActionListenerField
	private JButton viewMeetButton;
	@ActionListenerField
	private JButton yourArticleButton;
	@ActionListenerField
	private JButton storeArticleButton;
	@ActionListenerField
	private JButton myTradesButton;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
