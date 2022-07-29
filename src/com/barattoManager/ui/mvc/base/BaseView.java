package com.barattoManager.ui.mvc.base;

import javax.swing.*;
import java.awt.*;

public class BaseView extends JPanel {

	public BaseView() {
		setSize(600, 430);
	}

	public BaseView(Dimension dimension) {
		setSize(dimension);
	}


	public void addMainPanel(JPanel jPanel) {
		add(jPanel).setPreferredSize(getSize());
	}

	public JPanel getJPanel() {
		return this;
	}
}
