package com.barattoManager.ui.customComponents.buttons;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.ui.customComponents.optionPane.CreateNewMeetPanel;
import com.barattoManager.utils.parser.StringParser;
import com.barattoManager.utils.parser.TimeParser;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that contains buttons for the meet editor view
 */
public class MeetEditorButtons extends JPanel {

	/**
	 * Add new meet button
	 */
	private static final String ADD_NEW_MEET_BUTTON = "Aggiungi un nuovo luogo di incontro";
	/**
	 * Creation of new meet title
	 */
	private static final String CREATION_OF_NEW_MEET_TITLE = "Creazione di un nuovo luogo di incontro";


	/**
	 * {@link MeetEditorButtons} constructor
	 */
	public MeetEditorButtons() {
		var addNewMeetButton = new JButton(ADD_NEW_MEET_BUTTON);

		addNewMeetButton.addActionListener(e -> addNewMeet());

		add(addNewMeetButton, BorderLayout.WEST);
		setVisible(true);
	}

	/**
	 * Method used to add new meet
	 */
	private void addNewMeet() {
		var createNewMeetPanel = new CreateNewMeetPanel();
		int result = JOptionPane.showOptionDialog(
				this,
				createNewMeetPanel,
				CREATION_OF_NEW_MEET_TITLE,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (result == JOptionPane.OK_OPTION) {

			try {
				MeetManagerFactory.getManager().addNewMeet(
						createNewMeetPanel.getCity(),
						createNewMeetPanel.getSquare(),
						createNewMeetPanel.getSelectedDays(),
						TimeParser.hourToMinuteTime(createNewMeetPanel.getStartTimeField().getText()),
						TimeParser.hourToMinuteTime(createNewMeetPanel.getEndTimeField().getText()),
						StringParser.stringIntoInteger(createNewMeetPanel.getDaysBeforeExpireField())
				);
			} catch (AlreadyExistException | IllegalValuesException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
