package com.barattoManager.ui.mvc.dialogs.selectCategoryArticle;

import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class SelectCategoryArticleView implements BaseView {
    private final JPanel mainPanel;

    public SelectCategoryArticleView() {
        this.mainPanel = new JPanel();
    }

    @Override
    public JPanel getMainJPanel() {
        return mainPanel;
    }
}
