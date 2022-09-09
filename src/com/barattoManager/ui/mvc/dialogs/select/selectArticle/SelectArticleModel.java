package com.barattoManager.ui.mvc.dialogs.select.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.Model;

import java.util.List;

/**
 * Model of {@link SelectArticleController} that contains the data
 */
public class SelectArticleModel implements Model {

	private final List<Article> articles;

	private Article articleSelected;

	/**
	 * Constructor of the class
	 *
	 * @param articles {@link List} of {@link Article}
	 */
	public SelectArticleModel(List<Article> articles) {
		this.articles = articles;
	}

	/**
	 * Method used to get the articles
	 *
	 * @return {@link List} of article
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * Method used to get the selected article
	 *
	 * @return selected {@link Article}
	 */
	public Article getArticleSelected() {
		return articleSelected;
	}

	/**
	 * Method used to set the selected article
	 *
	 * @param articleSelected Selected {@link Article}
	 */
	public void setArticleSelected(Article articleSelected) {
		this.articleSelected = articleSelected;
	}
}
