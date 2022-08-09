package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NewMeetView implements BaseView {

	private static final String[] DAYS = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	private static final String LABEL_FILL_ALL_FIELDS = "Compila tutti i campi richiesti per inserire un nuovo luogo di incontro.";
	private static final String LABEL_START_TIME = "Orario di inizio (HH:MM): ";
	private static final String LABEL_END_TIME = "Orario di Fine (HH:MM): ";

	private final ArrayList<DaysCheckboxListener> listeners;
	private final JPanel mainPanel;
	@DocumentListenerField
	private final JTextField cityField;
	@DocumentListenerField
	private final JTextField squareField;
	private final JCheckBox[] daysCheckbox;
	@DocumentListenerField
	private final JTextField startTimeField;
	@DocumentListenerField
	private final JTextField endTimeField;
	@DocumentListenerField
	private final JTextField daysBeforeExpireField;

	public NewMeetView() {
		listeners = new ArrayList<>();
		mainPanel = new JPanel();
		cityField = new JTextField(26);
		squareField = new JTextField(20);
		daysCheckbox = new JCheckBox[7];
		startTimeField = new JTextField(24);
		endTimeField = new JTextField(24);
		daysBeforeExpireField = new JTextField(24);

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(LABEL_FILL_ALL_FIELDS));

		var cityPanel = new JPanel();
		cityPanel.add(new JLabel("Città:"));
		cityPanel.add(cityField);

		var squarePanel = new JPanel();
		squarePanel.add(new JLabel("Via/Piazza/Strada:"));
		squarePanel.add(squareField);

		var daysPanel = new JPanel();
		daysPanel.add(new JLabel("Giorni"));

		var checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(2, 4));

		for (int i = 0; i < DAYS.length; i++) {
			daysCheckbox[i] = new JCheckBox(DAYS[i]);

			final int finalI = i;
			daysCheckbox[i].addActionListener(e -> fireDaysCheckboxListener(DAYS[finalI], daysCheckbox[finalI].isSelected()));

			checkBoxPanel.add(daysCheckbox[i]);
		}
		daysPanel.add(checkBoxPanel);

		var startTimePanel = new JPanel();
		startTimePanel.add(new JLabel(LABEL_START_TIME));
		startTimePanel.add(startTimeField);

		var endTimePanel = new JPanel();
		endTimePanel.add(new JLabel(LABEL_END_TIME));
		endTimePanel.add(endTimeField);

		var daysBeforeExpirePanel = new JPanel();
		daysBeforeExpirePanel.add(new JLabel("Giorni per confermare:"));
		daysBeforeExpirePanel.add(daysBeforeExpireField);

		mainPanel.add(cityPanel);
		mainPanel.add(squarePanel);
		mainPanel.add(daysPanel);
		mainPanel.add(startTimePanel);
		mainPanel.add(endTimePanel);
		mainPanel.add(daysBeforeExpirePanel);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public String getCityField() {
		return cityField.getText();
	}

	public String getSquareField() {
		return squareField.getText();
	}

	public String getStartTimeField() {
		return startTimeField.getText();
	}

	public String getEndTimeField() {
		return endTimeField.getText();
	}

	public String getDaysBeforeExpireField() {
		return daysBeforeExpireField.getText();
	}

	public void addDaysCheckboxListener(DaysCheckboxListener listener) {
		listeners.add(listener);
	}

	private void fireDaysCheckboxListener(String day, boolean selected) {
		listeners.forEach(daysCheckboxListener -> daysCheckboxListener.daysUpdate(day, selected));
	}
}

