package com.barattoManager.ui.components;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.ui.panels.optionPane.RegisterNewUserPanel;
import com.barattoManager.ui.panels.optionPane.RegisterNewUserPanel;
import com.barattoManager.user.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the initial view
 */
public class InitialMenuUI extends JPanel {

    /**
     * Main JPanel
     */
    private JPanel mainPanel;
    /**
     * JButton used to log in
     */
    private JButton loginButton;
    private JButton RegisterButton;

    /**
     * {@link InitialMenuUI} constructor
     * @param dimension Dimension of JPanel
     * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
     * @param contentPanel {@link JPanel} object that contains every cards
     */
    public InitialMenuUI(Dimension dimension, CardLayout cardLayout, JPanel contentPanel) {
        // JPanel conf
        setVisible(true);
        add(mainPanel);

        mainPanel.setPreferredSize(dimension);

        loginButton.addActionListener(e -> {cardLayout.show(contentPanel, ComponentsName.LOGIN.toString());});

        RegisterButton.addActionListener(e -> {
            var newViewerPanel = new RegisterNewUserPanel();
            int result = JOptionPane.showOptionDialog(
                    this,
                    newViewerPanel,
                    "Registrazione di un nuovo fruitore",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    UserManager.getInstance().addNewUser(
                            newViewerPanel.getUsernameField().getText(),
                            AppConfigurator.getInstance().getPasswordSetting("default_pwd"),
                            false
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            """
                            Nuovo fruitore creato correttamente.
                                        
                            Username: %s
                            Password: %s
                            """.formatted(newViewerPanel.getUsernameField().getText(), AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
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
