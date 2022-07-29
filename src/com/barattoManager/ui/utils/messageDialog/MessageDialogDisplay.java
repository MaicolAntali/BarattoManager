package com.barattoManager.ui.utils.messageDialog;

import javax.swing.*;
import java.awt.*;

public class MessageDialogDisplay {

	private Component parentComponent;
	private String message = "";
	private String title = "BarattoManager";
	private int messageType = JOptionPane.INFORMATION_MESSAGE;

	public MessageDialogDisplay setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
		return this;
	}

	public MessageDialogDisplay setMessage(String message) {
		this.message = message;
		return this;
	}

	public MessageDialogDisplay setTitle(String title) {
		this.title = title;
		return this;
	}

	public MessageDialogDisplay setMessageType(int messageType) {
		this.messageType = messageType;
		return this;
	}

	public void show() {
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
}
