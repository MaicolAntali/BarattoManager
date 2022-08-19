package com.barattoManager.ui.mvc.viewer.storeArticleViewer;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuController;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuModel;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuView;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.StoreTreeModel;
import com.barattoManager.ui.utils.ControllerNames;

public class ViewerStoreArticleController implements BaseController {

	private final ViewerStoreArticleView view;

	public ViewerStoreArticleController(ViewerStoreArticleView view) {
		this.view = view;

		var articleTreeController = new ArticleTreeController(
				new StoreTreeModel(ArticleManagerFactory.getManager().getArticleMap().values().stream().toList()),
				new ArticleTreeView()
		);

		var storeArticleMenu = new StoreArticleMenuController(
				new StoreArticleMenuModel(articleTreeController),
				new StoreArticleMenuView()
		);

		this.view.setTreePanel(articleTreeController.getView().getMainJPanel());
		this.view.setMenuPanel(storeArticleMenu.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "questionButton")
	private void clickOnQuestionButton() {
		System.out.println("Informazioni");
	}
}
