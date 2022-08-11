package com.barattoManager.ui.mvc.viewer.yourArticle;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuController;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuView;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.YourArticleTreeModel;
import com.barattoManager.ui.utils.ControllerNames;

public class YourArticleController implements BaseController {

	private final YourArticleView view;

	public YourArticleController(YourArticleView view) {
		this.view = view;

		var articleTreeController = new ArticleTreeController(
				new YourArticleTreeModel(ArticleManagerFactory.getManager().getArticleMap().values().stream().toList()),
				new ArticleTreeView()
		);

		this.view.setTreePanel(articleTreeController.getView().getMainJPanel());

		var yourArticleMenu = new YourArticleMenuController(new YourArticleMenuView());
		this.view.setMenuPanel(yourArticleMenu.getView().getMainJPanel());

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
