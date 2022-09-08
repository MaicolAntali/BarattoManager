package com.barattoManager.ui.mvc.viewer.homepage;

import com.barattoManager.ui.action.actions.RegisterShowControllerAction;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.viewer.categoryViewer.ViewerCategoryController;
import com.barattoManager.ui.mvc.viewer.categoryViewer.ViewerCategoryView;
import com.barattoManager.ui.mvc.viewer.meetViewer.ViewerMeetController;
import com.barattoManager.ui.mvc.viewer.meetViewer.ViewerMeetView;
import com.barattoManager.ui.mvc.viewer.storeArticleViewer.ViewerStoreArticleController;
import com.barattoManager.ui.mvc.viewer.storeArticleViewer.ViewerStoreArticleView;
import com.barattoManager.ui.mvc.viewer.tradesViewer.ViewerTradesController;
import com.barattoManager.ui.mvc.viewer.tradesViewer.ViewerTradesView;
import com.barattoManager.ui.mvc.viewer.yourArticleViewer.ViewerYourArticleController;
import com.barattoManager.ui.mvc.viewer.yourArticleViewer.ViewerYourArticleView;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handles the events of the {@link ViewerHomepageView}
 */
public class ViewerHomepageController implements Controller {

	private final ViewerHomepageView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerHomepageView} represent the view of the controller
	 */
	public ViewerHomepageController(ViewerHomepageView view) {
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

	@ActionListenerFor(sourceField = "viewCategoryButton")
	private void clickOnViewCategoryButton() {
		new RegisterShowControllerAction(
				ControllerNames.CATEGORY_VIEWER,
				new ViewerCategoryController(new ViewerCategoryView())
		).run();
	}

	@ActionListenerFor(sourceField = "viewMeetButton")
	private void clickOnViewMeetButton() {
		new RegisterShowControllerAction(
				ControllerNames.MEET_VIEWER,
				new ViewerMeetController(new ViewerMeetView())
		).run();
	}

	@ActionListenerFor(sourceField = "yourArticleButton")
	private void clickOnYourArticleButton() {
		new RegisterShowControllerAction(
				ControllerNames.ARTICLE_VIEWER,
				new ViewerYourArticleController(new ViewerYourArticleView())
		).run();
	}

	@ActionListenerFor(sourceField = "storeArticleButton")
	private void clickOnStoreArticleButton() {
		new RegisterShowControllerAction(
				ControllerNames.STORE_VIEWER,
				new ViewerStoreArticleController(new ViewerStoreArticleView())
		).run();
	}

	@ActionListenerFor(sourceField = "myTradesButton")
	private void clickOnMyTradesButton() {
		new RegisterShowControllerAction(
				ControllerNames.TRADES_VIEWER,
				new ViewerTradesController(new ViewerTradesView())
		).run();
	}
}
