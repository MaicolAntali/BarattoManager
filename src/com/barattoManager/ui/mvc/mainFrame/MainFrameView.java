package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public class MainFrameView extends JPanel implements View {
	private JPanel mainPanel;
	private JPanel contentPanel;
	private JLabel versionLabel;

	private JPanel loadedPanel;

	public MainFrameView() {
		setSize(600, 500);
	}

	public void setVersion(String versionLabel) {
		this.versionLabel.setText(versionLabel);
	}

	public void updateContentPanel(JPanel panel) {
		if (loadedPanel != null)
			contentPanel.remove(loadedPanel);

		loadedPanel = panel;

		contentPanel.add(panel);
		contentPanel.repaint();
		contentPanel.revalidate();
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
