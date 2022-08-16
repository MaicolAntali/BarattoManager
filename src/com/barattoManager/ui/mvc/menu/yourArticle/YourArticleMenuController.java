package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.services.Store;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.menu.action.actions.CancelOfferAction;
import com.barattoManager.ui.mvc.menu.action.actions.NewArticleAction;

public class YourArticleMenuController implements BaseController {

	private final YourArticleMenuModel model;
	private final YourArticleMenuView view;

	public YourArticleMenuController(YourArticleMenuModel model, YourArticleMenuView view) {
		this.model = model;
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

	@ActionListenerFor(sourceField = "newArticleAction")
	private void clickOnNewArticleAction() {
		new NewArticleAction(Store.getLoggedUser(), model.getTreeController()).run();
	}

	@ActionListenerFor(sourceField = "cancelArticleAction")
	private void clickOnCancelArticleAction() {
		new CancelOfferAction(Store.getLoggedUser(), model.getTreeController()).run();
	}
}
