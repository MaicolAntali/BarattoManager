package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.login.LoginController;

/**
 * Model of {@link NewFieldController} that contains the data
 */
public class NewFieldModel implements Model {

	private String fieldName;
	private boolean fieldRequired;

	/**
	 * Constructor of the class
	 */
	public NewFieldModel() {
		this.fieldName = "";
		this.fieldRequired = false;
	}

	/**
	 * Method used to get the field name as a {@link String}
	 * @return Name of the field
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Method used to set the field name as a {@link String}
	 * @param fieldName {@link String} to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Method used to get the field requirement as a {@link Boolean}
	 * @return field requirement
	 */
	public boolean isFieldRequired() {
		return fieldRequired;
	}

	/**
	 * Method used to set the field requirement as a {@link Boolean}
	 * @param fieldRequired {@link Boolean} to set
	 */
	public void setFieldRequired(boolean fieldRequired) {
		this.fieldRequired = fieldRequired;
	}
}
