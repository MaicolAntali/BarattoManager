package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;

public class NewFieldController implements BaseController {

	private final NewFieldModel model;
	private final NewFieldView view;

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
	public BaseView getView() {
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
