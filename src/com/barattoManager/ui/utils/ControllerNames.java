package com.barattoManager.ui.utils;

public enum ControllerNames {

	HOMEPAGE("homepage"),
	LOGIN("login"),

	REGISTER_VIEWER("register-viewer"),

	HOMEPAGE_CONFIGURATOR("homepage-configurator"),
	CATEGORY_EDITOR("category-editor"),
	REGISTER_CONFIGURATOR("register-configurator");

	private final String controllerName;

	ControllerNames(String controllerName) {
		this.controllerName = controllerName;
	}

	@Override
	public String toString() {
		return this.controllerName;
	}
}
