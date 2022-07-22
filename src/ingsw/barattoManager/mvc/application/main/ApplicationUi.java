package ingsw.barattoManager.mvc.application.main;

import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.utils.AppConfigurator;
import ingsw.barattoManager.mvc.application.homepage.HomepageController;
import ingsw.barattoManager.mvc.application.homepage.HomepageUi;

import javax.swing.*;
import java.awt.*;

/**
 * Class that defines the {@link JFrame} of the application.<br/>
 * The application uses {@link CardLayout} to manage all the panels that need to be shown in the JFrame.
 */
public class ApplicationUi extends JFrame {

	private static final String TITLE_BARATTO_MANAGER = "Baratto Manager";

	/**
	 * Constant defining the default panel size
	 */
	public static final Dimension CONTENT_PANEL_DEFAULT_DIMENSION = new Dimension(600, 430);
	private final CardLayout cardLayout = new CardLayout();

	private JPanel mainPanel;
	private JPanel panelContainer;
	private JLabel versionLabel;

	/**
	 * Constructor of the class
	 */
	public ApplicationUi() {
		this.setTitle(TITLE_BARATTO_MANAGER);
		this.setSize(600, 500);
		this.setResizable(false);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

		/* FIXME: togliere commento ai demoni
		MeetManagerFactory.getManager().runUpdaterDaemon();
		TradeManagerFactory.getManager().runDaemonChecker(); */

		versionLabel.setText(AppConfigurator.getInstance().getAppDataAsText("version"));

		HomepageController homepageController = new HomepageController(
				new HomepageUi(CONTENT_PANEL_DEFAULT_DIMENSION)
		);

		panelContainer.setLayout(cardLayout);
		panelContainer.add(homepageController.getView(), ComponentsName.INITIAL_PANEL.toString());
	}

	public JPanel getPanelContainer() {
		return panelContainer;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}
}