package com.barattoManager.ui.mvc.dialogs.selectArticle;

import com.barattoManager.services.article.Article;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class ArticleComboBoxRenderer extends BasicComboBoxRenderer {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		if (value == null)
			setText(" ");
		else
			setText(((Article) value).getArticleName());

		return this;
	}
}
