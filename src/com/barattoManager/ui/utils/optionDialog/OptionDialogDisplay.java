package com.barattoManager.ui.utils.optionDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JOptionPane#showOptionDialog(Component, Object, String, int, int, Icon, Object[], Object)}
 */
public class OptionDialogDisplay {

	private Component parentComponent;
	private Object message = "";
	private String title = "BarattoManager";
	private int messageType = JOptionPane.INFORMATION_MESSAGE;

	/**
	 * Method used to set a parent {@link Component}
	 *
	 * @param parentComponent parent {@link Component}
	 * @return {@link OptionDialogDisplay} instance
	 */
	public OptionDialogDisplay setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
		return this;
	}

	/**
	 * Method used to set the message
	 *
	 * @param message {@link String} that represent the message
	 * @return {@link OptionDialogDisplay} instance
	 */
	public OptionDialogDisplay setMessage(Object message) {
		this.message = message;
		return this;
	}

	/**
	 * Method used to set the title
	 *
	 * @param title {@link String} that represent the title
	 * @return {@link OptionDialogDisplay} instance
	 */
	public OptionDialogDisplay setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Method used to set the message type
	 *
	 * @param messageType {@code int} that represent the message type
	 * @return {@link OptionDialogDisplay} instance
	 */
	public OptionDialogDisplay setMessageType(int messageType) {
		this.messageType = messageType;
		return this;
	}

	/**
	 * Method used to generate the {@link JOptionPane#showOptionDialog(Component, Object, String, int, int, Icon, Object[], Object)}
	 *
	 * @return an integer indicating the option chosen by the user
	 */
	public int show() {
		return JOptionPane.showOptionDialog(parentComponent, message, title, JOptionPane.OK_CANCEL_OPTION, messageType, null, null, null);
	}
}
