package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.meet.MeetManager;
import com.barattoManager.ui.customComponents.optionPane.CreateNewMeetPanel;
import com.barattoManager.ui.customComponents.tree.event.RepaintEventHandler;
import com.barattoManager.utils.TimeParse;

import javax.swing.*;
import java.awt.*;

public class MeetEditorButtons extends JPanel {

	private final RepaintEventHandler repaintEventHandler;


	public MeetEditorButtons(RepaintEventHandler repaintEventHandler) {
		this.repaintEventHandler = repaintEventHandler;

		var addNewMeetButton = new JButton("Aggiungi un nuovo luogo di incontro");


		addNewMeetButton.addActionListener(e -> addNewMeet());

		add(addNewMeetButton, BorderLayout.WEST);
		setVisible(true);
	}

	private void addNewMeet() {
		try {
			var createNewMeetPanel = new CreateNewMeetPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					createNewMeetPanel,
					"Creazione di un nuovo luogo di incontro",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				MeetManager.getInstance().addNewMeet(
						createNewMeetPanel.getCity(),
						createNewMeetPanel.getSquare(),
						createNewMeetPanel.getSelectedDays(),
						TimeParse.hourToMinuteTime(createNewMeetPanel.getStartTimeField().getText()),
						TimeParse.hourToMinuteTime(createNewMeetPanel.getEndTimeField().getText())
				);
				repaintEventHandler.fireListeners();
			}
		} catch (IllegalValuesException | AlreadyExistException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
