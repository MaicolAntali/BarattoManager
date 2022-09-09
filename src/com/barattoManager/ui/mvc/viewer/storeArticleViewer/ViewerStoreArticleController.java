package com.barattoManager.ui.mvc.viewer.storeArticleViewer;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuController;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuModel;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuView;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.StoreTreeModel;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

/**
 * Controller that handle the events of the {@link ViewerStoreArticleView}
 */
public class ViewerStoreArticleController implements Controller {

	private static final String HELP_MESSAGE = "Nello store dei articoli puoi visualizzare gli articoli che gli altri utenti hanno messo in baratto " +
			"\n Per scambiare un tuo articolo con uno di questi puoi cliccare sul menu in alto a sinistra e scegliere l'opzione di scambio.";
	private final ViewerStoreArticleView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerStoreArticleView} represent the view of the controller
	 */
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
		new MessageDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessageType(JOptionPane.INFORMATION_MESSAGE)
				.setTitle("HELP")
				.setMessage(HELP_MESSAGE)
				.show();
	}
}
