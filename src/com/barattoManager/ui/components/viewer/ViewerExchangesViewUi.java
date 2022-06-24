package com.barattoManager.ui.components.viewer;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.trade.TradeTree;

import javax.swing.*;
import java.awt.*;

public class ViewerExchangesViewUi extends JPanel{
    public static final String HELP_MESSAGE = """
			In questa pagina puoi visualizzare I tuoi articoli
			Per effettuare un operazione su un tuo articolo puoi cliccare sul menu in alto al sinistra e scegliere di:
				 - Aggiungere un nuovo articolo da barattare;
				 - Cancellare l'offerta di un articolo.""";
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

        // var articleTreeMenu = new DashboardMenuFactory().createMenuObject().createMenu(repaintEventHandler, user, articleTree);
        // centerPanel.add(articleTreeMenu, BorderLayout.NORTH);
        centerPanel.add(tradeTree);


        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

        questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                HELP_MESSAGE,
                "Help",
                JOptionPane.INFORMATION_MESSAGE));
    }
}
