package com.barattoManager.ui.mvc.configurator.offerView;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeController;
import com.barattoManager.ui.mvc.tree.article.ArticleTreeView;
import com.barattoManager.ui.mvc.tree.article.ConfiguratorTreeModel;
import com.barattoManager.ui.utils.ControllerNames;

public class ViewOfferController implements BaseController {

	private final ViewOfferView view;

	public ViewOfferController(ViewOfferView view) {
		this.view = view;

		var articleTreeController = new ArticleTreeController(
				new ConfiguratorTreeModel(ArticleManagerFactory.getManager().getArticleMap().values().stream().toList()),
				new ArticleTreeView()
		);
		this.view.setTreePanel(articleTreeController.getView().getMainJPanel());

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
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
	}
}
