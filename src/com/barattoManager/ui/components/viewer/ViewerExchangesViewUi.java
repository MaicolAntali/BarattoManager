package com.barattoManager.ui.components.viewer;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.menu.factory.TradeMenuFactory;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.trade.TradeTree;

import javax.swing.*;
import java.awt.*;

/**
 *  Class used to create a JPanel that represent the trades view (only viewer)
 */
public class ViewerExchangesViewUi extends JPanel implements DataChangeListener {
    /**
     * Help message: In this view you can see your trades
     * from the left corner menu you can do the following commands:
     * -Accept a trade
     * -Accept and reschedule a trade
     */
    public static final String HELP_MESSAGE =  """
			In questa pagina puoi visualizzare i tuoi scambi
			Per effettuare un operazione su uno scambio puoi cliccare sul menu in alto al sinistra e scegliere di:
				 - Accettare una proposta di scambio;
				 - Accettare e riprogrammare una proposta di scambio.""";
    /**
     * {@link User}
     */
    private final User user;

    /**
     *{@link Tree}
     */
    private Tree tradeTree;
    private JMenuBar menu;
    /**
     * Main Panel
     */
    private JPanel mainPanel;
    /**
     * Center Panel {@code BorderLayout.CENTER}
     */
    private JPanel centerPanel;
    /**
     * Back button to {@link ViewerHomeUi}
     */
    private JButton backToHomeButton;
    private JButton questionButton;

    /**
     * {@link ViewerExchangesViewUi} constructor
     * @param dimension Dimension of JPanel
     * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
     * @param panelContainer {@link JPanel} object that contains every cards
     * @param user {@link User}
     */
    public ViewerExchangesViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
        this.user = user;
        this.tradeTree = new TradeTree(user);

        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        menu = new TradeMenuFactory().createMenuObject().createMenu(user, tradeTree);
        centerPanel.add(menu, BorderLayout.NORTH);
        centerPanel.add(tradeTree);


        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

        questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                HELP_MESSAGE,
                "Help",
                JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void update() {
        centerPanel.remove(tradeTree);
        centerPanel.remove(menu);

        this.tradeTree = new TradeTree(user);
        this.menu = new TradeMenuFactory().createMenuObject().createMenu(user, tradeTree);

        centerPanel.add(menu, BorderLayout.NORTH);
        centerPanel.add(tradeTree);

        centerPanel.repaint();
        centerPanel.revalidate();
    }
}
