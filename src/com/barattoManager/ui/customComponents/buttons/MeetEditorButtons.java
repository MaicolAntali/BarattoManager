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
 * Class used to create a {@link JPanel} that contains buttons for the configurator to create new meet.
 */
public class MeetEditorButtons extends JPanel {

	private static final String ADD_NEW_MEET_BUTTON = "Aggiungi un nuovo luogo di incontro";
	private static final String CREATION_OF_NEW_MEET_TITLE = "Creazione di un nuovo luogo di incontro";
	private static final String ERROR_EMPTY_OR_INCORRECT_DATA = "I dati immessi sono vuoti o non corretti ";


	/**
	 * Constructor of the class
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

			int dayBeforeExpire;
			try {
				dayBeforeExpire = StringParser.stringIntoInteger(createNewMeetPanel.getDaysBeforeExpireField());
			} catch (IllegalValuesException e) {
				JOptionPane.showMessageDialog(this, ERROR_EMPTY_OR_INCORRECT_DATA, "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				MeetManagerFactory.getManager().addNewMeet(
						createNewMeetPanel.getCity(),
						createNewMeetPanel.getSquare(),
						createNewMeetPanel.getSelectedDays(),
						TimeParser.hourToMinuteTime(createNewMeetPanel.getStartTimeField().getText()),
						TimeParser.hourToMinuteTime(createNewMeetPanel.getEndTimeField().getText()),
						dayBeforeExpire
				);
			} catch (AlreadyExistException | IllegalValuesException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
