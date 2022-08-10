package com.barattoManager.ui.mvc.viewer.articleViewer;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuController;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuView;

import javax.swing.*;

public class ViewerArticleView implements BaseView {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	@ActionListenerField
	private JButton loadJsonQuestionButton;
	private JPanel treePanel;
	private JPanel menuPanel;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}

	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel.add(menuPanel);
	}
}
