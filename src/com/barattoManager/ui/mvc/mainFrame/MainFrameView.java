package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;

public class MainFrameView extends BaseView {
	private JPanel mainPanel;
	private JPanel contentPanel;
	private JLabel versionLabel;

	public MainFrameView() {
		super(new Dimension(600, 470));
		addMainPanel(mainPanel);
	}

	public void setVersion(String versionLabel) {
		this.versionLabel.setText(versionLabel);
	}

}
