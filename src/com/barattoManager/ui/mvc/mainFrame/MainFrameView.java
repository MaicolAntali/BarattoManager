package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows you the main frame
 */
public class MainFrameView extends JPanel implements View {
	private JPanel mainPanel;
	private JPanel contentPanel;
	private JLabel versionLabel;

	private JPanel loadedPanel;

	/**
	 * Constructor of the class
	 */
	public MainFrameView() {
		setSize(600, 500);
	}

	/**
	 * Method used to set the version
	 *
	 * @param versionLabel {@link String} to set
	 */
	public void setVersion(String versionLabel) {
		this.versionLabel.setText(versionLabel);
	}

	/**
	 * Method used to update the content panel
	 *
	 * @param panel {@link JPanel} which will be added to the content panel
	 */
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
