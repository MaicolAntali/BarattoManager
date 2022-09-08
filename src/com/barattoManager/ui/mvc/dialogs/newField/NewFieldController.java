package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;

/**
 * Controller that handle the events of the {@link NewFieldView} and update the data in the {@link NewFieldModel}
 */
public class NewFieldController implements Controller {

	private final NewFieldModel model;
	private final NewFieldView view;

	/**
	 * Constructor of the class
	 *
	 * @param model {@link NewFieldModel} represent the model of the controller
	 * @param view {@link  NewFieldView} represent the view of the controller
	 */
	public NewFieldController(NewFieldModel model, NewFieldView view) {
		this.model = model;
		this.view = view;

		DocumentListenerInstaller.processAnnotations(this, view);
		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public NewFieldModel getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@DocumentListenerFor(sourceField = "fieldName")
	private void categoryNameHasChanged() {
		model.setFieldName(view.getFieldName());
	}

	@ActionListenerFor(sourceField = "fieldRequired")
	private void categoryDescriptionHasChanged() {
		model.setFieldRequired(view.getFieldRequired());
	}
}
