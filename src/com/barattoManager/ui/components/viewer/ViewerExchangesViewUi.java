package com.barattoManager.ui.components.viewer;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.event.RepaintListener;
import com.barattoManager.ui.customComponents.menu.factory.TradeMenuFactory;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.trade.TradeTree;

import javax.swing.*;
import java.awt.*;

public class ViewerExchangesViewUi extends JPanel implements RepaintListener {
    public static final String HELP_MESSAGE = "...";
    private final User user;

    private Tree tradeTree;
    private JPanel mainPanel;
    private JButton backToHomeButton;
    private JPanel centerPanel;
    private JButton questionButton;

    public ViewerExchangesViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
        this.user = user;
        this.tradeTree = new TradeTree(user);

        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
        repaintEventHandler.addListener(this);

        var tradeMenu = new TradeMenuFactory().createMenuObject().createMenu(repaintEventHandler, user, tradeTree);
        centerPanel.add(tradeMenu, BorderLayout.NORTH);
        centerPanel.add(tradeTree);


        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

        questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                HELP_MESSAGE,
                "Help",
                JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void repaintComponents() {
        centerPanel.remove(tradeTree);

        this.tradeTree = new TradeTree(user);
        centerPanel.add(tradeTree);

        centerPanel.repaint();
        centerPanel.revalidate();
    }
}
