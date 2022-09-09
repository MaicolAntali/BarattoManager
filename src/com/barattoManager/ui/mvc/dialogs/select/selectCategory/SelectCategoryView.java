package com.barattoManager.ui.mvc.dialogs.select.selectCategory;

import com.barattoManager.ui.mvc.dialogs.select.SelectView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.util.List;

/**
 * View used to create the selection a category
 */
public class SelectCategoryView extends SelectView<String> {


	private static final String LABEL_CHOOSE_AN_CATEGORY = "Seleziona la categoria:";
	private static final String THERE_ARENT_ANY_CATEGORY = "Non ci sono Categorie disponibili.";

	/**
	 * Constructor of the class
	 *
	 * @param clazz {@link Class}
	 */
	public SelectCategoryView(Class<String> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<String> data) {
		getMainJPanel().add(new JLabel(LABEL_CHOOSE_AN_CATEGORY));

		if (data.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage(THERE_ARENT_ANY_CATEGORY)
					.show();
			return;
		}

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
