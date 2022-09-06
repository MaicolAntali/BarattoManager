package com.barattoManager.ui.mvc.dialogs.select;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class SelectView<T> extends ActionNotifierHandler implements View {

	private final JPanel mainPanel;
	private final JComboBox<T> comboBox;
	private final Class<T> clazz;

	public SelectView(Class<T> clazz) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		this.clazz = clazz;

		comboBox = new JComboBox<>();
		comboBox.addActionListener(e -> fireActionNotifierListener(e.getActionCommand()));
	}

	public abstract void draw(List<T> data);

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public T getSelectedObject() {
		return clazz.cast(comboBox.getSelectedItem());
	}

	public JComboBox<T> getComboBox() {
		return comboBox;
	}
}
