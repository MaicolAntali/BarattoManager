package com.barattoManager.ui;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.manager.factory.TradeManagerFactory;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.components.InitialMenuUI;
import com.barattoManager.ui.components.LoginUI;
import com.barattoManager.utils.AppConfigurator;

import javax.swing.*;
import java.awt.*;

/**
 * Class (that {@code extends JFrame}) used to instance every other view in a cardLayout and set the JFrame.
 */
public class BarattoManagerGui extends JFrame {
	/**
	 * Baratto manager title
	 */
	private static final String TITLE_BARATTO_MANAGER = "Baratto Manager";
	/**
	 * Default dimensions of views
	 */
	public static final Dimension CONTENT_PANEL_DEFAULT_DIMENSION = new Dimension(600, 430); // height - 70

	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * Panel with a cardLayout that contains each view
	 */
	private JPanel panelContainer;
	/**
	 * Label that show the app version
	 */
	private JLabel versionLabel;

	/**
	 * {@link BarattoManagerGui} constructor
	 */
	public BarattoManagerGui() {
		this.setTitle(TITLE_BARATTO_MANAGER);
		this.setSize(600, 500);
		this.setResizable(false);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

		MeetManagerFactory.getManager().runUpdaterDaemon();
		TradeManagerFactory.getManager().runDaemonChecker();

		versionLabel.setText(AppConfigurator.getInstance().getAppDataAsText("version"));

		CardLayout cardLayout = new CardLayout();

		var loginUi = new LoginUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
		EventFactory.getUsersEvent().addListener(loginUi);

		panelContainer.setLayout(cardLayout);
		panelContainer.add(new InitialMenuUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.INITIAL_PANEL.toString());
		panelContainer.add(loginUi, ComponentsName.LOGIN.toString());
	}
}