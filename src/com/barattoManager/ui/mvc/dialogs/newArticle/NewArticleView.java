package com.barattoManager.ui.mvc.dialogs.newArticle;

import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class NewArticleView implements BaseView {

    private final JPanel mainPanel;

    public NewArticleView() {
        this.mainPanel = new JPanel();

    }

    @Override
    public JPanel getMainJPanel() {
        return mainPanel;
    }
}
