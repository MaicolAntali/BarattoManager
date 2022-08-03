package com.barattoManager.ui.utils.optionDialog;

import javax.swing.*;
import java.awt.*;

public class OptionDialogDisplay {

	private Component parentComponent;
	private Object message = "";
	private String title = "BarattoManager";
	private int optionType = JOptionPane.OK_CANCEL_OPTION;
	private int messageType = JOptionPane.INFORMATION_MESSAGE;

	public OptionDialogDisplay setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
		return this;
	}

	public OptionDialogDisplay setMessage(Object message) {
		this.message = message;
		return this;
	}

	public OptionDialogDisplay setTitle(String title) {
		this.title = title;
		return this;
	}

	public OptionDialogDisplay setOptionType(int optionType) {
		this.optionType = optionType;
		return this;
	}

	public OptionDialogDisplay setMessageType(int messageType) {
		this.messageType = messageType;
		return this;
	}

	public int show() {
		return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, null, null, null);
	}
}
