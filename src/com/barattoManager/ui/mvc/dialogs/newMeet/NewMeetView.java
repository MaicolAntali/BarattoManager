package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.dialogs.NewArticle.ArticleFieldHasChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * View that shows the panel used create a new meet
 */
public class NewMeetView implements View {

	private static final String[] DAYS = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	private static final String LABEL_FILL_ALL_FIELDS = "Compila tutti i campi richiesti per inserire un nuovo luogo di incontro.";
	private static final String LABEL_START_TIME = "Orario di inizio (HH:MM): ";
	private static final String LABEL_END_TIME = "Orario di Fine (HH:MM): ";
	private static final String LABEL_DAYS_TO_CONFIRM = "Giorni per confermare:";

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

	/**
	 * Constructor of the class
	 */
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
		daysBeforeExpirePanel.add(new JLabel(LABEL_DAYS_TO_CONFIRM));
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

	/**
	 * Method used to get the city of the meet from a {@link JTextField}
	 *
	 * @return City of the meet as a {@link String}
	 */
	public String getCityField() {
		return cityField.getText();
	}

	/**
	 * Method used to get the square of the meet from a {@link JTextField}
	 *
	 * @return square of the meet as a {@link String}
	 */
	public String getSquareField() {
		return squareField.getText();
	}

	/**
	 * Method used to get the start time of the meet from a {@link JTextField}
	 *
	 * @return start time of the meet as a {@link String}
	 */
	public String getStartTimeField() {
		return startTimeField.getText();
	}

	/**
	 * Method used to get the end time of the meet from a {@link JTextField}
	 *
	 * @return end time of the meet as a {@link String}
	 */
	public String getEndTimeField() {
		return endTimeField.getText();
	}

	/**
	 * Method used to get the day before expire of the meet from a {@link JTextField}
	 *
	 * @return day before expire of the meet as a {@link String}
	 */
	public String getDaysBeforeExpireField() {
		return daysBeforeExpireField.getText();
	}

	/**
	 * Method used to add a listener
	 *
	 * @param listener {@link DaysCheckboxListener}
	 */
	public void addDaysCheckboxListener(DaysCheckboxListener listener) {
		listeners.add(listener);
	}

	/**
	 * Method used to fire a {@link ArticleFieldHasChangeListener}
	 *
	 * @param day      that represent the day of the week
	 * @param selected that represent whether the checkbox is selected or not
	 */
	private void fireDaysCheckboxListener(String day, boolean selected) {
		listeners.forEach(daysCheckboxListener -> daysCheckboxListener.daysUpdate(day, selected));
	}
}

