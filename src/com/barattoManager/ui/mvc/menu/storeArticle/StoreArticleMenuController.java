package com.barattoManager.ui.mvc.menu.storeArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public class StoreArticleMenuController implements BaseController {
	private final StoreArticleMenuView view;

	public StoreArticleMenuController(StoreArticleMenuView view) {
		this.view = view;

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

	@ActionListenerFor(sourceField = "newTradeAction")
	private void clickOnNewTradeAction() {
		System.out.println("Scambia articolo");
	}
}
