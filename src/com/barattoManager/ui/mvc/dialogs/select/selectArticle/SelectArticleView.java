package com.barattoManager.ui.mvc.dialogs.select.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.dialogs.select.SelectView;

import javax.swing.*;
import java.util.List;

/**
 * View used to create the selection an article
 */
public class SelectArticleView extends SelectView<Article> {

	public static final String LABEL_SELECT_ARTICLE_TO_EXCHANGE = "Seleziona un articolo da barattare";

	/**
	 * Constructor of the class
	 *
	 * @param clazz {@link Class}
	 */
	public SelectArticleView(Class<Article> clazz) {
		super(clazz);
	}

	@Override
	public void draw(List<Article> data) {
		getMainJPanel().add(new JLabel(LABEL_SELECT_ARTICLE_TO_EXCHANGE));

		getComboBox().setRenderer(new ArticleComboBoxRenderer());

		data.forEach(getComboBox()::addItem);

		getComboBox().setSelectedIndex(-1);

		getMainJPanel().add(getComboBox());
	}
}
