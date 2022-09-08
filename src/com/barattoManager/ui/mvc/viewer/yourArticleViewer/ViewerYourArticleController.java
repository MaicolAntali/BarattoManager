package com.barattoManager.ui.mvc.viewer.yourArticleViewer;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuController;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuModel;
import com.barattoManager.ui.mvc.menu.yourArticle.YourArticleMenuView;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.YourArticleTreeModel;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handle the events of the {@link ViewerYourArticleView}
 */
public class ViewerYourArticleController implements Controller {

	private final ViewerYourArticleView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerYourArticleView} represent the view of the controller
	 */
	public ViewerYourArticleController(ViewerYourArticleView view) {
		this.view = view;

		var articleTreeController = new ArticleTreeController(
				new YourArticleTreeModel(ArticleManagerFactory.getManager().getArticleMap().values().stream().toList()),
				new ArticleTreeView()
		);

		this.view.setTreePanel(articleTreeController.getView().getMainJPanel());

		var yourArticleMenu = new YourArticleMenuController(
				new YourArticleMenuModel(articleTreeController),
				new YourArticleMenuView()
		);
		this.view.setMenuPanel(yourArticleMenu.getView().getMainJPanel());

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
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
