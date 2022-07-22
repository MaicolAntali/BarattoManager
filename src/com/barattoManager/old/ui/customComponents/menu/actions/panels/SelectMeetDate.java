package com.barattoManager.old.ui.customComponents.menu.actions.panels;

import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.manager.factory.MeetManagerFactory;
import com.barattoManager.old.sample.meet.Meet;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that permits to select a date for the meeting
 */
public class SelectMeetDate extends JPanel {

	private static final String SELECT_DAY_EXCHANGE = "Seleziona il giorno dello scambio:";
	private static final String ERROR_NO_MEET_AVAILABLE = "Non ci sono meet disponibili";
	private final JComboBox<Meet> meetComboBox = new JComboBox<>();

	/**
	 * Constructor of the class
	 *
	 * @throws IllegalValuesException Is thrown if there are no available meet
	 */
	public SelectMeetDate() throws IllegalValuesException {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(SELECT_DAY_EXCHANGE));

		meetComboBox.setRenderer(new MeetComboBoxCustomRenderer());

		var meets = MeetManagerFactory.getManager().getAvailableMeet();
		if (meets.isEmpty()) {
			throw new IllegalValuesException(ERROR_NO_MEET_AVAILABLE);
		}

		meets.forEach(meetComboBox::addItem);

		mainPanel.add(meetComboBox);

		add(mainPanel);
		setVisible(true);
	}

	/**
	 * Method used to get the selected {@link Meet}
	 *
	 * @return selected {@link Meet}
	 */
	public Meet getSelectedMeet() {
		return (Meet) meetComboBox.getSelectedItem();
	}

	static class MeetComboBoxCustomRenderer extends BasicComboBoxRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			var item = (Meet) value;

			setText("%s ~ %s ~ %s ~ [%s-%s]".formatted(
					item.getCity(),
					item.getSquare(),
					item.getDay(),
					item.getStartTime(),
					item.getEndTime()
			));

			return this;
		}
	}
}
