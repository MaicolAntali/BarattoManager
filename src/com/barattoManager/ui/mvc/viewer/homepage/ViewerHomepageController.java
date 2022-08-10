package com.barattoManager.ui.mvc.viewer.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.viewer.categoryViewer.ViewerCategoryController;
import com.barattoManager.ui.mvc.viewer.categoryViewer.ViewerCategoryView;
import com.barattoManager.ui.mvc.viewer.meetViewer.ViewerMeetController;
import com.barattoManager.ui.mvc.viewer.meetViewer.ViewerMeetView;
import com.barattoManager.ui.mvc.viewer.yourArticle.YourArticleController;
import com.barattoManager.ui.mvc.viewer.yourArticle.YourArticleView;
import com.barattoManager.ui.utils.ControllerNames;

public class ViewerHomepageController implements BaseController {

	private final ViewerHomepageView view;

	public ViewerHomepageController(ViewerHomepageView view) {
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

	@ActionListenerFor(sourceField = "viewCategoryButton")
	private void clickOnViewCategoryButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewerCategoryController(new ViewerCategoryView()), ControllerNames.CATEGORY_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.CATEGORY_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "viewMeetButton")
	private void clickOnViewMeetButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewerMeetController(new ViewerMeetView()), ControllerNames.MEET_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.MEET_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "yourArticleButton")
	private void clickOnYourArticleButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new YourArticleController(new YourArticleView()), ControllerNames.ARTICLE_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.ARTICLE_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "storeArticleButton")
	private void clickOnStoreArticleButton() {
		System.out.println("Store articoli");
	}

	@ActionListenerFor(sourceField = "myTradesButton")
	private void clickOnMyTradesButton() {
		System.out.println("I tuoi scambi");
	}
}
