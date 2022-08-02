package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class HomepageView extends JPanel implements BaseView {

	private JPanel mainPanel;
	@ActionListenerField
	private JButton loginButton;
	@ActionListenerField
	private JButton registerButton;

	public HomepageView() {
		setSize(PANEL_DIMENSION);
	}
	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
