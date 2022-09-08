package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;

/**
 * Controller that handle the events of the {@link NewCategoryView} and update the data in the {@link NewCategoryModel}
 */
public class NewCategoryController implements Controller {

	private final NewCategoryModel model;
	private final NewCategoryView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link NewCategoryModel} represent the model of the controller
	 * @param view {@link  NewCategoryView} represent the view of the controller
	 */
	public NewCategoryController(NewCategoryModel model, NewCategoryView view) {
		this.model = model;
		this.view = view;

		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewCategoryModel getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@DocumentListenerFor(sourceField = "categoryNameField")
	private void categoryNameHasChanged() {
		model.setCategoryName(view.getCategoryName());
	}

	@DocumentListenerFor(sourceField = "categoryDescriptionField")
	private void categoryDescriptionHasChanged() {
		model.setCategoryDescription(view.getCategoryDescription());
	}
}
