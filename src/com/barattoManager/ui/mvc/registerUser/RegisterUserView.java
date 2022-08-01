package com.barattoManager.ui.mvc.registerUser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;

public class RegisterUserView extends JPanel implements BaseRegisterUserView {

	private final ArrayList<RegisterUserAbortButtonListener> abortButtonListeners;
	private final ArrayList<RegisterUserRegisterButtonListener> registerButtonListeners;

	private final ArrayList<RegistrerUserUsernameTextFieldUpdateListener> userUsernameFieldUpdateListeners;

	private JPanel mainPanel;
	private JButton registerButton;
	private JButton abortButton;
	private JTextField usernameField;

	public RegisterUserView() {
		abortButtonListeners = new ArrayList<>();
		registerButtonListeners = new ArrayList<>();
		userUsernameFieldUpdateListeners = new ArrayList<>();

		abortButton.addActionListener(e -> fireAbortButtonListeners());
		registerButton.addActionListener(e -> fireRegisterButtonListeners());
		usernameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				fireUsernameTextFieldUpdateListeners(usernameField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				fireUsernameTextFieldUpdateListeners(usernameField.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				fireUsernameTextFieldUpdateListeners(usernameField.getText());
			}
		});

		setSize(PANEL_DIMENSION);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	@Override
	public void addAbortButtonListeners(RegisterUserAbortButtonListener listener) {
		this.abortButtonListeners.add(listener);
	}

	@Override
	public void addRegisterButtonListeners(RegisterUserRegisterButtonListener listener) {
		this.registerButtonListeners.add(listener);
	}

	@Override
	public void addUsernameTextFieldUpdateListeners(RegistrerUserUsernameTextFieldUpdateListener listener) {
		this.userUsernameFieldUpdateListeners.add(listener);
	}

	protected void fireAbortButtonListeners() {
		this.abortButtonListeners.forEach(RegisterUserAbortButtonListener::clickOnAbort);
	}

	protected void fireRegisterButtonListeners() {
		this.registerButtonListeners.forEach(RegisterUserRegisterButtonListener::clickOnRegister);
	}

	protected void fireUsernameTextFieldUpdateListeners(String usernameUpdated) {
		this.userUsernameFieldUpdateListeners.forEach(listener -> listener.usernameFieldHasChange(usernameUpdated));
	}
}
