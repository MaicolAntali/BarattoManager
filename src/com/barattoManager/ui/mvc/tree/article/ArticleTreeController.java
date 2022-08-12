package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;

public class ArticleTreeController extends TreeController<Article> {


	public ArticleTreeController(YourArticleTreeModel model, ArticleTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		ArticleUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
