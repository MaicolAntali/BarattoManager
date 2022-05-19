package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.meet.MeetManager;
import com.barattoManager.ui.customComponents.optionPane.CreateNewMeetPanel;

import javax.swing.*;
import java.awt.*;

public class MeetEditorButtons extends JPanel {


	public MeetEditorButtons() {

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
						createNewMeetPanel.getStartTimeMiutues(),
						createNewMeetPanel.getEndTimeMitutes());
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
