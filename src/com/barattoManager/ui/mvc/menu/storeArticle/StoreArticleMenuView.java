package com.barattoManager.ui.mvc.menu.storeArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;

public class StoreArticleMenuView implements BaseView {

    private final JPanel mainPanel;
    @ActionListenerField
    private final JMenuItem newTradeAction;

    public StoreArticleMenuView() {
        mainPanel = new JPanel();
        var articleMenu = new JMenu("Scambi");

        newTradeAction = articleMenu.add(new JMenuItem("Scambia Articolo"));
        newTradeAction.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        var menuBar = new JMenuBar();
        menuBar.add(articleMenu);
        menuBar.setPreferredSize(new Dimension(500, 30));

        mainPanel.add(menuBar);
    }

    @Override
    public JPanel getMainJPanel() {
        return mainPanel;
    }

    public JMenuItem getNewTradeAction() {
        return newTradeAction;
    }
}
