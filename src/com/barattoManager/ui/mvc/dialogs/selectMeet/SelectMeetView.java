package com.barattoManager.ui.mvc.dialogs.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectMeetView implements BaseView {

	private final JPanel mainPanel;

	@ActionListenerField
	private final JComboBox<Meet> meetJComboBox;

	public SelectMeetView() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		meetJComboBox = new JComboBox<>();
	}

	public void draw(List<Meet> meets) {
		mainPanel.add(new JLabel("Seleziona un incontro:"));

		if (meets.isEmpty()) {
			new MessageDialogDisplay()
					.setParentComponent(mainPanel)
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage("Non ci sono incontri disponibili.")
					.show();
			return;
		}

		meetJComboBox.setRenderer(new MeetComboBoxRenderer());

		meets.forEach(meetJComboBox::addItem);

		meetJComboBox.setSelectedIndex(-1);

		mainPanel.add(meetJComboBox);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public Meet getSelectedMeet() {
		return ((Meet) meetJComboBox.getSelectedItem());
	}
}
