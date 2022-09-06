package com.barattoManager.ui.mvc.dialogs.select.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.util.List;

public class SelectMeetView extends SelectView<Meet> {


	public SelectMeetView(Class<Meet> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<Meet> data) {
		getMainJPanel().add(new JLabel("Seleziona un incontro:"));

		if (data.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non ci sono incontri disponibili.")
					.show();
			return;
		}

		getComboBox().setRenderer(new MeetComboBoxRenderer());

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
