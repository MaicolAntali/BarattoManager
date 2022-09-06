package com.barattoManager.ui.mvc.dialogs.select.selectCategory;

import com.barattoManager.ui.mvc.dialogs.select.SelectView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.util.List;

public class SelectCategoryView extends SelectView<String> {


	public SelectCategoryView(Class<String> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<String> data) {
		getMainJPanel().add(new JLabel("Seleziona la categoria:"));

		if (data.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non ci sono Categorie disponibili.")
					.show();
			return;
		}

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
