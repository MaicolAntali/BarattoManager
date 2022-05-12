package com.barattoManager.ui.components.ViewerHomeUi;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.components.ConfiguratorCategoryEditorUi;
import com.barattoManager.ui.panels.optionPane.RegisterNewConfiguratorPanel;
import com.barattoManager.user.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the configurator home view
 */
public class ViewerHomeUi extends JPanel {
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * JButton used to go in the {@link ConfiguratorCategoryEditorUi} view
	 */
	private JButton editCategoryButton;
	/**
	 * JButton used to add a new configurator
	 */
	private JButton addNewConfigurator;

	/**
	 * {@link ViewerHomeUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ViewerHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		editCategoryButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_CATEGORY_EDITOR.toString()));

		addNewConfigurator.addActionListener(e -> {
			var newConfiguratorPanel = new RegisterNewConfiguratorPanel();
			int result = JOptionPane.showOptionDialog(
					this,
					newConfiguratorPanel,
					"Registrazione di un nuovo configuratore",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
			);

			if (result == JOptionPane.OK_OPTION) {
				try {
					UserManager.getInstance().addNewUser(
							newConfiguratorPanel.getUsernameField().getText(),
							AppConfigurator.getInstance().getPasswordSetting("default_pwd"),
							true
					);

					JOptionPane.showMessageDialog(
							this,
							"""
                            Nuovo configuratore creato correttamente.
                                        
                            Username: %s
                            Password: %s
							""".formatted(newConfiguratorPanel.getUsernameField().getText(), AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
							"Registrazione corretta",
							JOptionPane.INFORMATION_MESSAGE
							);
				} catch (AlreadyExistException | EmptyStringException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
