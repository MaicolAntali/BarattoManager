package com.barattoManager.ui.mvc.dialogs.select.selectArticle;

import com.barattoManager.services.article.Article;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

/**
 * Class used to specify the custom renderer for {@link JComboBox}
 */
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
