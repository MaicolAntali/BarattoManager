package com.barattoManager.ui.mvc.dialogs.selectArticle;

import com.barattoManager.services.article.Article;
import com.barattoManager.ui.mvc.base.BaseModel;

import java.util.List;

public class SelectArticleModel implements BaseModel {

	private final List<Article> articles;

	private Article articleSelected;

	public SelectArticleModel(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public Article getArticleSelected() {
		return articleSelected;
	}

	public void setArticleSelected(Article articleSelected) {
		this.articleSelected = articleSelected;
	}
}
