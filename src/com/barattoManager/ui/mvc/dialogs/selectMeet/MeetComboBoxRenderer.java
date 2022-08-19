package com.barattoManager.ui.mvc.dialogs.selectMeet;

import com.barattoManager.services.meet.Meet;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class MeetComboBoxRenderer extends BasicComboBoxRenderer {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		if (value == null)
			setText(" ");
		else {
			var meet = ((Meet) value);
			setText("%s ~ %s ~ %s ~ [%s-%s]".formatted(
					meet.getCity(),
					meet.getSquare(),
					meet.getDay(),
					meet.getStartTime(),
					meet.getEndTime()
			));
		}


		return this;
	}
}
