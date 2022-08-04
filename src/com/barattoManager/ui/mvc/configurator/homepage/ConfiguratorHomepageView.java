package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class ConfiguratorHomepageView implements BaseView {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton configCategoryButton;
	@ActionListenerField
	private JButton configMeetButton;
	@ActionListenerField
	private JButton addNewConfigurator;
	@ActionListenerField
	private JButton showOffer;
	@ActionListenerField
	private JButton loadJsonButton;
	@ActionListenerField
	private JButton loadJsonQuestionButton;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
