package com.barattoManager.ui.mvc.viewer.categoryViewer;

import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeController;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeView;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Controller that handle the events of the {@link ViewerCategoryView}
 */
public class ViewerCategoryController implements Controller {

	private final ViewerCategoryView view;

	/**
	 * Constructor of the class
	 *
	 * @param view {@link ViewerCategoryView} represent the view of the controller
	 */
	public ViewerCategoryController(ViewerCategoryView view) {
		this.view = view;

		var categoryTreeController = new CategoryTreeController(
				new CategoryTreeModel(
						CategoryManagerFactory.getManager().getRootCategoryMap().values().stream().toList()
				),
				new CategoryTreeView()
		);

		this.view.setTreePanel(categoryTreeController.getView().getMainJPanel());

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

	@ActionListenerFor(sourceField = "backToHome")
	private void clickOnBackToHome() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_VIEWER.toString());
	}
}
