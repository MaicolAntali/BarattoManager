package com.barattoManager.ui.mvc.menu.storeArticle;

import com.barattoManager.services.Store;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.menu.action.actions.NewTradeAction;

public class StoreArticleMenuController implements Controller {

	private final StoreArticleMenuModel model;
	private final StoreArticleMenuView view;

	public StoreArticleMenuController(StoreArticleMenuModel model, StoreArticleMenuView view) {
		this.model = model;
		this.view = view;

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

	@ActionListenerFor(sourceField = "newTradeAction")
	private void clickOnNewTradeAction() {
		new NewTradeAction(Store.getLoggedUser(), model.getTreeController()).run();
	}
}
