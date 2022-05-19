package com.barattoManager.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateNewMeetPanel extends JPanel {

	private static final String[] DAYS = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};

	private final JTextField cityField = new JTextField(26);
	private final JTextField squareField = new JTextField(20);
	private final JCheckBox[] daysCheckbox = new JCheckBox[7];
	private final JTextField startTimeField = new JTextField(24);
	private final JTextField endTimeField = new JTextField(24);


	public CreateNewMeetPanel() {
		var mainPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Inserisci tutti i campi richiesti per inseirere un nuovo luogo di incontro."));

		var cityPanel = new JPanel();
		cityPanel.add(new JLabel("Città:"));
		cityPanel.add(cityField);
		mainPanel.add(cityPanel);

		var squarePanel = new JPanel();
		squarePanel.add(new JLabel("Via/Piazza/Strada:"));
		squarePanel.add(squareField);
		mainPanel.add(squarePanel);

		var daysPanel = new JPanel();
		daysPanel.add(new JLabel("Giorni"));
		var checkBoxPanel = new JPanel();

		checkBoxPanel.setLayout(new GridLayout(2, 4));
		for (int i = 0; i < DAYS.length; i++) {
			daysCheckbox[i] = new JCheckBox(DAYS[i]);
			checkBoxPanel.add(daysCheckbox[i]);
		}
		daysPanel.add(checkBoxPanel);
		mainPanel.add(daysPanel);


		var startTimePanel = new JPanel();
		startTimePanel.add(new JLabel("Orari di inizio (HH:MM): "));
		startTimePanel.add(startTimeField);
		mainPanel.add(startTimePanel);

		var endTimePanel = new JPanel();
		endTimePanel.add(new JLabel("Orari di Fine (HH:MM): "));
		endTimePanel.add(endTimeField);
		mainPanel.add(endTimePanel);

		add(mainPanel);
		setVisible(true);
	}

	public String getCity() {
		return cityField.getText();
	}

	public String getSquare() {
		return squareField.getText();
	}

	public ArrayList<String> getSelectedDays() {
		var days = new ArrayList<String>();

		for (JCheckBox checkBox : daysCheckbox) {
			if (checkBox.isSelected()) {
				days.add(checkBox.getText());
			}
		}

		return days;
	}

	public JTextField getStartTimeField() {
		return startTimeField;
	}

	public JTextField getEndTimeField() {
		return endTimeField;
	}
}
