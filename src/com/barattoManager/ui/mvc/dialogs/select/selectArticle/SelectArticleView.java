package com.barattoManager.ui.mvc.dialogs.select.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;

import javax.swing.*;
import java.util.List;

public class SelectArticleView extends SelectView<Article> {

	public SelectArticleView(Class<Article> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<Article> data) {
		getMainJPanel().add(new JLabel("Seleziona un articolo da barattare"));

		getComboBox().setRenderer(new ArticleComboBoxRenderer());

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
