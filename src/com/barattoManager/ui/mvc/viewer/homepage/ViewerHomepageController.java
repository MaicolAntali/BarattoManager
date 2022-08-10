package com.barattoManager.ui.mvc.viewer.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.viewer.categoryView.ViewCategoryController;
import com.barattoManager.ui.mvc.viewer.categoryView.ViewCategoryView;
import com.barattoManager.ui.mvc.viewer.meetView.ViewMeetController;
import com.barattoManager.ui.mvc.viewer.meetView.ViewMeetView;
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
				new ViewCategoryController(new ViewCategoryView()), ControllerNames.CATEGORY_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.CATEGORY_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "viewMeetButton")
	private void clickOnViewMeetButton() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new ViewMeetController(new ViewMeetView()), ControllerNames.MEET_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.MEET_VIEWER.toString());
	}

	@ActionListenerFor(sourceField = "yourArticleButton")
	private void clickOnYourArticleButton() {
		System.out.println("I tuoi articoli");
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
