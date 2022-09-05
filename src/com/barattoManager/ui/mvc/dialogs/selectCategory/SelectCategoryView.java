package com.barattoManager.ui.mvc.dialogs.selectCategory;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectCategoryView implements View {

	private final JPanel mainPanel;

	@ActionListenerField
	private final JComboBox<String> comboBox;

	public SelectCategoryView() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		comboBox = new JComboBox<>();
	}

	public void draw(List<String> categoriesName) {
		mainPanel.add(new JLabel("Seleziona la categoria:"));

		if (categoriesName.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(mainPanel)
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non ci sono Categorie disponibili.")
					.show();
			return;
		}

		categoriesName.forEach(comboBox::addItem);

		comboBox.setSelectedIndex(-1);

		mainPanel.add(comboBox);
	}

	protected String getSelectedCategoryName() {
		return String.valueOf(comboBox.getSelectedItem());
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
