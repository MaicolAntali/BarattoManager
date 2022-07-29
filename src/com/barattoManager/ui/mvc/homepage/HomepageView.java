package com.barattoManager.ui.mvc.homepage;

import javax.swing.*;
import java.util.ArrayList;

public class HomepageView extends JPanel implements BaseHomepageView {

	private final ArrayList<HomepageLoginButtonListener> loginButtonListeners;
	private final ArrayList<HomepageRegisterButtonListener> registerButtonListeners;

	private JPanel mainPanel;
	private JButton loginButton;
	private JButton registerButton;

	public HomepageView() {
		setSize(PANEL_DIMENSION);
		this.loginButtonListeners = new ArrayList<>();
		this.registerButtonListeners = new ArrayList<>();

		loginButton.addActionListener(e -> fireLoginButtonListeners());
		registerButton.addActionListener(e -> fireRegisterButtonListeners());
	}
	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	@Override
	public void addLoginButtonListeners(HomepageLoginButtonListener listener) {
		this.loginButtonListeners.add(listener);
	}

	@Override
	public void addRegisterButtonListeners(HomepageRegisterButtonListener listener) {
		this.registerButtonListeners.add(listener);
	}

	protected void fireLoginButtonListeners() {
		loginButtonListeners.forEach(HomepageLoginButtonListener::clickOnLogin);
	}

	protected void fireRegisterButtonListeners() {
		registerButtonListeners.forEach(HomepageRegisterButtonListener::clickOnRegister);
	}
}
