package com.barattoManager.ui.customComponents.menu.actions.panels;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.model.meet.Meet;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class SelectMeetDate extends JPanel {

	/**
	 * Label: select the day of the exchange
	 */
	private static final String SELECT_DAY_EXCHANGE = "Seleziona il giorno dello scambio:";
	/**
	 * Error: there are no meetings available
	 */
	private static final String ERROR_NO_MEET_AVAILABLE = "Non ci sono meet disponibili";

	private final JComboBox<Meet> meetComboBox = new JComboBox<>();

	public SelectMeetDate() throws IllegalValuesException {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(SELECT_DAY_EXCHANGE));

		meetComboBox.setRenderer(new MeetComboBoxCustomRenderer());

		var meets = MeetManager.getInstance().getAvailableMeet();
		if (meets.isEmpty()) {
			throw new IllegalValuesException(ERROR_NO_MEET_AVAILABLE);
		}

		meets.forEach(meetComboBox::addItem);

		mainPanel.add(meetComboBox);

		add(mainPanel);
		setVisible(true);
	}

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
