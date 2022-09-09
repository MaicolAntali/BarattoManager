package com.barattoManager.ui.mvc.dialogs.select;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Abstract class used to represent a select view <br>
 * Extends {@link ActionNotifierHandler} because it is used to handle the actions <br>
 * This class must be extended from all views of the package select
 */
public abstract class SelectView<T> extends ActionNotifierHandler implements View {

	private final JPanel mainPanel;
	private final JComboBox<T> comboBox;
	private final Class<T> clazz;

	/**
	 * Constructor of the class
	 *
	 * @param clazz {@link Class}
	 */
	public SelectView(Class<T> clazz) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		this.clazz = clazz;

		comboBox = new JComboBox<>();
		comboBox.addActionListener(e -> fireActionNotifierListener(e.getActionCommand()));
	}

	/**
	 * Method used to draw a view
	 * @param data {@link List} used to draw the view
	 */
	public abstract void draw(List<T> data);

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	/**
	 * Method used to get a selected object
	 * @return selected object
	 */
	public T getSelectedObject() {
		return clazz.cast(comboBox.getSelectedItem());
	}

	/**
	 * Method used to get a {@link JComboBox}
	 * @return {@link JComboBox}
	 */
	public JComboBox<T> getComboBox() {
		return comboBox;
	}
}
