package com.barattoManager.ui.mvc.dialogs.select.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.util.List;

/**
 * View used to create the selection of a meet
 */
public class SelectMeetView extends SelectView<Meet> {


	private static final String LABEL_CHOOSE_MEET = "Seleziona un incontro:";
	private static final String THERE_ARENT_MEET_AVAILABLE = "Non ci sono incontri disponibili.";

	/**
	 * Constructor of the class
	 *
	 * @param clazz {@link Class}
	 */
	public SelectMeetView(Class<Meet> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<Meet> data) {
		getMainJPanel().add(new JLabel(LABEL_CHOOSE_MEET));

		if (data.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage(THERE_ARENT_MEET_AVAILABLE)
					.show();
			return;
		}

		getComboBox().setRenderer(new MeetComboBoxRenderer());

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
