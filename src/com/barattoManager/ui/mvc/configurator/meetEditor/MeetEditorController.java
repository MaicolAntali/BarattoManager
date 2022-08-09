package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.meet.MeetManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetController;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetModel;
import com.barattoManager.ui.mvc.dialogs.newMeet.NewMeetView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeController;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeModel;
import com.barattoManager.ui.mvc.tree.meet.MeetTreeView;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;
import com.barattoManager.utils.parser.StringParser;
import com.barattoManager.utils.parser.TimeParser;

import javax.swing.*;

public class MeetEditorController implements BaseController {

	private final MeetEditorView view;

	public MeetEditorController(MeetEditorView view) {
		this.view = view;

		MeetTreeController meetTreeController = new MeetTreeController(
				new MeetTreeModel(
						MeetManagerFactory.getManager().getAvailableMeet()
				),
				new MeetTreeView()
		);

		this.view.setTreePanel(meetTreeController.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
	}

	@ActionListenerFor(sourceField = "addMeetButton")
	private void clickOnAddMeetButton() {

		var newMeetController = new NewMeetController(new NewMeetModel(), new NewMeetView());

		var result = new OptionDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessage(newMeetController.getView().getMainJPanel())
				.setTitle("Creazione di un nuovo incontro")
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
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage(e.getMessage())
						.show();
			}
		}
	}
}
