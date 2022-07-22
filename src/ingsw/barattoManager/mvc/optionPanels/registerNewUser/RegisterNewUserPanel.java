package ingsw.barattoManager.mvc.optionPanels.registerNewUser;

import ingsw.barattoManager.mvc.event.BaseEventHandler;
import ingsw.barattoManager.mvc.event.Event;
import ingsw.barattoManager.mvc.optionPanels.Panel;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} used to register a new user
 */
public class RegisterNewUserPanel extends Panel {
    private static final String LABEL_REGISTER_NEW_VIEWER = "Registrazione di un nuovo fruitore";
    private static final String LABEL_REGISTER_NEW_CONFIGURATOR = "Registrazione di un nuovo configuratore";
    private static final String LABEL_CHOOSE_USERNAME = "Scegli un username:";
    private final JTextField usernameField = new JTextField(13);
    private final JPanel registrationFormPanel = new JPanel();
    private final JPanel parentPanel;
    private final BaseEventHandler eventHandler;


    /**
     * Constructor of the class
     *
     * @param parentPanel    {@link JPanel Parent panel} of JOptionPane
     * @param isConfigurator Boolean value that tells if a user is a configurator
     * @param eventHandler {@link BaseEventHandler} used to fire event to the controller {@link RegisterNewUserController}
     */
    public RegisterNewUserPanel(JPanel parentPanel, boolean isConfigurator, BaseEventHandler eventHandler) {
        this.parentPanel = parentPanel;
        this.eventHandler = eventHandler;

        var panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel(isConfigurator ? LABEL_REGISTER_NEW_CONFIGURATOR : LABEL_REGISTER_NEW_VIEWER));

        var namePanel = new JPanel();
        namePanel.add(new JLabel(LABEL_CHOOSE_USERNAME));
        namePanel.add(usernameField);
        panel.add(namePanel);

        registrationFormPanel.add(panel);
        registrationFormPanel.setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }


    public void showUI() {
        int result = JOptionPane.showOptionDialog(
                parentPanel,
                registrationFormPanel,
                "Registrazione Utente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null
        );

        if (result == JOptionPane.OK_OPTION) {
            eventHandler.fireListener(new Event("JOptionPane_OK"));
        }

    }
}
