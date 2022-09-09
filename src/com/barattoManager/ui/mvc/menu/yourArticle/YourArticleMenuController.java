package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.services.Store;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.menu.action.actions.CancelOfferAction;
import com.barattoManager.ui.mvc.menu.action.actions.NewArticleAction;

/**
 * Controller that handle the events of the {@link YourArticleMenuView} and update the data in the {@link YourArticleMenuModel}
 */
public class YourArticleMenuController implements Controller {

	private final YourArticleMenuModel model;
	private final YourArticleMenuView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link YourArticleMenuModel} represent the model of the controller
	 * @param view  {@link YourArticleMenuView} represent the view of the controller
	 */
	public YourArticleMenuController(YourArticleMenuModel model, YourArticleMenuView view) {
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

	@ActionListenerFor(sourceField = "newArticleAction")
	private void clickOnNewArticleAction() {
		new NewArticleAction(Store.getLoggedUser(), model.getTreeController()).run();
	}

	@ActionListenerFor(sourceField = "cancelArticleAction")
	private void clickOnCancelArticleAction() {
		new CancelOfferAction(Store.getLoggedUser(), model.getTreeController()).run();
	}
}
