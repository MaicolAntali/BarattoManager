package com.barattoManager.ui.action.actions;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetController;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetModel;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;
import com.barattoManager.utils.parser.StringParser;
import com.barattoManager.utils.parser.TimeParser;

import javax.swing.*;

/**
 * Action used to add a meet
 */
public class AddMeetAction implements Action {
	private static final String TITLE_CREATION_OF_NEW_MEET = "Creazione di un nuovo incontro";
	private final JPanel parentPanel;

	/**
	 * Constructor of the class
	 *
	 * @param parentPanel The {@link JPanel} used to display the dialog
	 */
	public AddMeetAction(JPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	@Override
	public void run() {
		var newMeetController = new NewMeetController(new NewMeetModel(), new NewMeetView());

		var result = new OptionDialogDisplay()
				.setParentComponent(parentPanel)
				.setMessage(newMeetController.getView().getMainJPanel())
				.setTitle(TITLE_CREATION_OF_NEW_MEET)
				.setMessageType(JOptionPane.QUESTION_MESSAGE)
				.show();

		if (result == JOptionPane.OK_OPTION) {
			try {
				MeetManagerFactory.getManager()
						.addNewMeet(
								newMeetController.getModel().getCity(),
								newMeetController.getModel().getSquare(),
								newMeetController.getModel().getDays(),
								TimeParser.hourToMinuteTime(newMeetController.getModel().getStartTime()),
								TimeParser.hourToMinuteTime(newMeetController.getModel().getEndTime()),
								StringParser.stringIntoInteger(newMeetController.getModel().getDaysBeforeExpire())
						);
			} catch (InvalidArgumentException | AlreadyExistException e) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}
}
