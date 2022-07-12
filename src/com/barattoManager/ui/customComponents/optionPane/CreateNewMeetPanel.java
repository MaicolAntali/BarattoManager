package com.barattoManager.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to create a JPanel used to create a new meet
 */
public class CreateNewMeetPanel extends JPanel {
	/**
	 * Array of days
	 */
	private static final String[] DAYS = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	/**
	 * Fill all the field label
	 */
	private static final String LABEL_FILL_ALL_FIELDS = "Compila tutti i campi richiesti per inserire un nuovo luogo di incontro.";
	/**
	 * Start time label
	 */
	private static final String LABEL_START_TIME = "Orario di inizio (HH:MM): ";
	/**
	 * End time label
	 */
	private static final String LABEL_END_TIME = "Orario di Fine (HH:MM): ";

	/**
	 * City field
	 */
	private final JTextField cityField = new JTextField(26);
	/**
	 * Square field
	 */
	private final JTextField squareField = new JTextField(20);
	/**
	 * Days checkboxes
	 */
	private final JCheckBox[] daysCheckbox = new JCheckBox[7];
	/**
	 * Start time field
	 */
	private final JTextField startTimeField = new JTextField(24);
	/**
	 * End time field
	 */
	private final JTextField endTimeField = new JTextField(24);
	private final JTextField daysBeforeExpireField = new JTextField(24);


	/**
	 * {@link CreateNewMeetPanel} constructor
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
	 * Method used to get the {@link #cityField}
	 * @return {@link JTextField} object
	 */
	public String getCity() {
		return cityField.getText();
	}
	/**
	 * Method used to get the {@link #squareField}
	 * @return {@link JTextField} object
	 */
	public String getSquare() {
		return squareField.getText();
	}

	/**
	 * Method used to get the selected days
	 *
	 * @return {@link ArrayList<String>} object
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
	 * Method used to get the {@link #startTimeField}
	 * @return {@link JTextField} object
	 */
	public JTextField getStartTimeField() {
		return startTimeField;
	}

	/**
	 * Method used to get the {@link #endTimeField}
	 * @return {@link JTextField} object
	 */
	public JTextField getEndTimeField() {
		return endTimeField;
	}

	/**
	 * Method used to get the days before expire field
	 * @return the day before expire
	 */
	public String getDaysBeforeExpireField() {
		return daysBeforeExpireField.getText();
	}
}
