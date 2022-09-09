package com.barattoManager.ui.mvc.tree.article;

import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleUpdateDataEventFactory;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.TreeModel;

/**
 * Controller that handle the events of the {@link ArticleTreeView} and update the data in the {@link TreeModel}
 */
public class ArticleTreeController extends TreeController<Article> {


	/**
	 * Constructor of the class
	 *
	 * @param model {@link TreeModel} represent the model of the controller
	 * @param view {@link  ArticleTreeView} represent the view of the controller
	 */
	public ArticleTreeController(TreeModel<Article> model, ArticleTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		ArticleUpdateDataEventFactory.getEventHandler().addListener(model);
	}
}
