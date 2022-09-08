package com.barattoManager.ui.utils.messageDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JOptionPane#showMessageDialog(Component, Object, String, int)}
 */
public class MessageDialogDisplay {

	private Component parentComponent;
	private String message = "";
	private String title = "BarattoManager";
	private int messageType = JOptionPane.INFORMATION_MESSAGE;

	/**
	 * Method used to set the parent {@link Component}
	 *
	 * @param parentComponent parent {@link Component}
	 * @return {@link MessageDialogDisplay} instance
	 */
	public MessageDialogDisplay setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
		return this;
	}

	/**
	 * Method used to set the message
	 *
	 * @param message {@link String} represent message
	 * @return {@link MessageDialogDisplay} instance
	 */
	public MessageDialogDisplay setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Method used to set the title
	 *
	 * @param title {@link String} represent the title
	 * @return {@link MessageDialogDisplay} instance
	 */
	public MessageDialogDisplay setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Method used to set the message type
	 *
	 * @param messageType {@code int} represent message type
	 * @return {@link MessageDialogDisplay} instance
	 */
	public MessageDialogDisplay setMessageType(int messageType) {
		this.messageType = messageType;
		return this;
	}

	/**
	 * Method used to show a {@link JOptionPane#showMessageDialog(Component, Object, String, int, Icon)}
	 */
	public void show() {
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
}
