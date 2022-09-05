package com.barattoManager.ui.mvc.dialogs.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectArticleView implements View {

	private final JPanel mainPanel;

	@ActionListenerField
	private final JComboBox<Article> articleComboBox;

	public SelectArticleView() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		articleComboBox = new JComboBox<>();
	}

	public void draw(List<Article> articles) {
		mainPanel.add(new JLabel("Seleziona un articolo da barattare"));

		articleComboBox.setRenderer(new ArticleComboBoxRenderer());

		articles.forEach(articleComboBox::addItem);

		articleComboBox.setSelectedIndex(-1);

		mainPanel.add(articleComboBox);
	}

	public Article getSelectedArticle() {
		return ((Article) articleComboBox.getSelectedItem());
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}
}
