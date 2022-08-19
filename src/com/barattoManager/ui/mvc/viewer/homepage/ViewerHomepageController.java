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
import com.barattoManager.ui.mvc.viewer.storeArticleViewer.ViewerStoreArticleController;
import com.barattoManager.ui.mvc.viewer.storeArticleViewer.ViewerStoreArticleView;
import com.barattoManager.ui.mvc.viewer.tradesViewer.ViewerTradesController;
import com.barattoManager.ui.mvc.viewer.tradesViewer.ViewerTradesView;
import com.barattoManager.ui.mvc.viewer.yourArticleViewer.ViewerYourArticleController;
import com.barattoManager.ui.mvc.viewer.yourArticleViewer.ViewerYourArticleView;
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
				new ViewerYourArticleController(new ViewerYourArticleView()), ControllerNames.ARTICLE_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.ARTICLE_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "storeArticleButton")
	private void clickOnStoreArticleButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewerStoreArticleController(new ViewerStoreArticleView()), ControllerNames.STORE_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.STORE_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "myTradesButton")
	private void clickOnMyTradesButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewerTradesController(new ViewerTradesView()), ControllerNames.TRADES_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.TRADES_VIEWER.toString());
	}
}
