package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.mvc.base.BaseModel;

public class NewFieldModel implements BaseModel {

	private String fieldName;
	private boolean fieldRequired;

	public NewFieldModel() {
		this.fieldName = "";
		this.fieldRequired = false;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isFieldRequired() {
		return fieldRequired;
	}

	public void setFieldRequired(boolean fieldRequired) {
		this.fieldRequired = fieldRequired;
	}
}
