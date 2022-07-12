package com.barattoManager.ui.customComponents.menu.actions.panels;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.model.meet.Meet;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class SelectMeetDate extends JPanel {

	private final JComboBox<Meet> meetComboBox = new JComboBox<>();

	public SelectMeetDate() throws IllegalValuesException {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Seleziona il giorno dello scambio:"));

		meetComboBox.setRenderer(new MeetComboBoxCustomRenderer());

		var meets = MeetManagerFactory.getManager().getAvailableMeet();
		if (meets.isEmpty()) {
			throw new IllegalValuesException("Non ci sono meet disponibili");
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
