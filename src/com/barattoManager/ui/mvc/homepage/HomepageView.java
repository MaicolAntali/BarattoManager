package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows the homepage
 */
public class HomepageView implements View {

	private JPanel mainPanel;
	@ActionListenerField
	private JButton loginButton;
	@ActionListenerField
	private JButton registerButton;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
