package com.barattoManager.ui.customComponents.tree.meet;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.meet.MeetManager;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.optionPane.CreateNewMeetPanel;
import com.barattoManager.utils.StringParse;
import com.barattoManager.utils.TimeParse;

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
	 * {@link RepaintEventHandler} object
	 */
	private final RepaintEventHandler repaintEventHandler;


	/**
	 * {@link MeetEditorButtons} constructor
	 * @param repaintEventHandler object
	 */
	public MeetEditorButtons(RepaintEventHandler repaintEventHandler) {
		this.repaintEventHandler = repaintEventHandler;

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
				MeetManager.getInstance().addNewMeet(
						createNewMeetPanel.getCity(),
						createNewMeetPanel.getSquare(),
						createNewMeetPanel.getSelectedDays(),
						TimeParse.hourToMinuteTime(createNewMeetPanel.getStartTimeField().getText()),
						TimeParse.hourToMinuteTime(createNewMeetPanel.getEndTimeField().getText()),
						StringParse.strintToInt(createNewMeetPanel.getDaysBeforeExpireField())
				);
			} catch (AlreadyExistException | IllegalValuesException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);
			}

			repaintEventHandler.fireListeners();
		}
	}
}
