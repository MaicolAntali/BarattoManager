package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

public class YourArticleMenuController implements BaseController {

	private final YourArticleMenuView view;

	public YourArticleMenuController(YourArticleMenuView view) {
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
		System.out.println("Aggiungi articolo");
	}

	@ActionListenerFor(sourceField = "cancelArticleAction")
	private void clickOnCancelArticleAction() {
		System.out.println("Cancella articolo");
	}
}
