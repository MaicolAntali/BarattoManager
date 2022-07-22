package com.barattoManager.old.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to create a {@link JPanel} used to create a new meet
 */
public class CreateNewMeetPanel extends JPanel {
	private static final String[] DAYS = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	private static final String LABEL_FILL_ALL_FIELDS = "Compila tutti i campi richiesti per inserire un nuovo luogo di incontro.";
	private static final String LABEL_START_TIME = "Orario di inizio (HH:MM): ";
	private static final String LABEL_END_TIME = "Orario di Fine (HH:MM): ";
	private final JTextField cityField = new JTextField(26);
	private final JTextField squareField = new JTextField(20);
	private final JCheckBox[] daysCheckbox = new JCheckBox[7];
	private final JTextField startTimeField = new JTextField(24);
	private final JTextField endTimeField = new JTextField(24);
	private final JTextField daysBeforeExpireField = new JTextField(24);


	/**
	 * Constructor of the class
	 */
	public CreateNewMeetPanel() {
		var mainPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(LABEL_FILL_ALL_FIELDS));

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
		startTimePanel.add(new JLabel(LABEL_START_TIME));
		startTimePanel.add(startTimeField);
		mainPanel.add(startTimePanel);

		var endTimePanel = new JPanel();
		endTimePanel.add(new JLabel(LABEL_END_TIME));
		endTimePanel.add(endTimeField);
		mainPanel.add(endTimePanel);

		var daysBeforeExpirePanel = new JPanel();
		daysBeforeExpirePanel.add(new JLabel("Giorni per confermare:"));
		daysBeforeExpirePanel.add(daysBeforeExpireField);
		mainPanel.add(daysBeforeExpirePanel);

		add(mainPanel);
		setVisible(true);
	}

	/**
	 * Method used to get the {@link String} with the city
	 *
	 * @return {@link String} with the city
	 */
	public String getCity() {
		return cityField.getText();
	}

	/**
	 * Method used to get the {{@link String} with the square
	 *
	 * @return {@link String} with the square
	 */
	public String getSquare() {
		return squareField.getText();
	}

	/**
	 * Method used to get the {@link ArrayList<String>} with the selected days
	 *
	 * @return {@link ArrayList<String>} with the selected days
	 */
	public ArrayList<String> getSelectedDays() {
		var days = new ArrayList<String>();

		for (JCheckBox checkBox : daysCheckbox) {
			if (checkBox.isSelected()) {
				days.add(checkBox.getText().replace("ì", "i").toUpperCase());
			}
		}

		return days;
	}

	/**
	 * Method used to get the {@link JTextField} with the start time
	 *
	 * @return {@link JTextField} with the start time
	 */
	public JTextField getStartTimeField() {
		return startTimeField;
	}

	/**
	 * Method used to get the {@link JTextField} with the end time
	 *
	 * @return {@link JTextField} with the end time
	 */
	public JTextField getEndTimeField() {
		return endTimeField;
	}

	/**
	 * Method used to get the {@link String} with the day before expire
	 *
	 * @return {@link String} with the day before expire
	 */
	public String getDaysBeforeExpireField() {
		return daysBeforeExpireField.getText();
	}
}
