package ingsw.barattoManager.mvc.application.homepage;

import com.barattoManager.old.sample.user.User;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that shows the initial menu to the {@link User users}
 */
public class HomepageUi extends JPanel {

    private JPanel mainPanel;
    private JButton loginButton;
    private JButton RegisterButton;

    /**
     * Constructor of the class
     *
     * @param dimension    {@link Dimension} of the {@link JPanel} to be created
     */
    public HomepageUi(Dimension dimension) {
        // JPanel conf
        setVisible(true);
        add(mainPanel);

        mainPanel.setPreferredSize(dimension);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return RegisterButton;
    }
}
