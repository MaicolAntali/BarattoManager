package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public class ConfiguratorHomepageView extends ActionNotifierHandler implements View {
	private JPanel mainPanel;
	private JButton configCategoryButton;
	private JButton configMeetButton;
	private JButton addNewConfigurator;
	private JButton showOffer;
	private JButton loadJsonButton;
	private JButton loadJsonQuestionButton;

	public ConfiguratorHomepageView() {
		configCategoryButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		configMeetButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		addNewConfigurator.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		showOffer.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		loadJsonButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		loadJsonQuestionButton.addActionListener(e -> fireActionNotifierListener("info_json_load"));
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
