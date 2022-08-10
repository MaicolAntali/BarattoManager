package com.barattoManager.ui.utils;

public enum ControllerNames {

	HOMEPAGE("homepage"),
	LOGIN("login"),

	HOMEPAGE_VIEWER("homepage-viewer"),
	REGISTER_VIEWER("register-viewer"),
	CATEGORY_VIEWER("category-view"),
	MEET_VIEWER("meet-view"),

	HOMEPAGE_CONFIGURATOR("homepage-configurator"),
	CATEGORY_EDITOR("category-editor"),
	MEET_EDITOR("meet-editor"),
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
