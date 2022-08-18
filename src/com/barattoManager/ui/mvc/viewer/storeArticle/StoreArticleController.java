package com.barattoManager.ui.mvc.viewer.storeArticle;

import com.barattoManager.services.trade.TradeManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuController;
import com.barattoManager.ui.mvc.menu.storeArticle.StoreArticleMenuView;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeController;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeModel;
import com.barattoManager.ui.mvc.tree.trade.TradeTreeView;
import com.barattoManager.ui.utils.ControllerNames;

public class StoreArticleController implements BaseController {

	private final StoreArticleView view;

	public StoreArticleController(StoreArticleView view) {
		this.view = view;

		var storeArticleMenu = new StoreArticleMenuController(new StoreArticleMenuView());
		this.view.setMenuPanel(storeArticleMenu.getView().getMainJPanel());

		var tradeTreeController = new TradeTreeController(
				new TradeTreeModel(TradeManagerFactory.getManager().getTradeMap().values().stream().toList()),
				new TradeTreeView()
		);
		this.view.setTreePanel(tradeTreeController.getView().getMainJPanel());

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
