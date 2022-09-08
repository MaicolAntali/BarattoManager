package com.barattoManager.ui.utils;

/**
 * Enum used to represent the controller names
 */
public enum ControllerNames {

	HOMEPAGE("homepage"),
	LOGIN("login"),

	HOMEPAGE_VIEWER("homepage-viewer"),
	REGISTER_VIEWER("register-viewer"),
	CATEGORY_VIEWER("category-view"),
	MEET_VIEWER("meet-view"),
	ARTICLE_VIEWER("article-viewer"),
	STORE_VIEWER("store-viewer"),
	TRADES_VIEWER("trades-viewer"),

	HOMEPAGE_CONFIGURATOR("homepage-configurator"),
	CATEGORY_EDITOR("category-editor"),
	MEET_EDITOR("meet-editor"),
	REGISTER_CONFIGURATOR("register-configurator"),
	OFFER_VIEW_CONFIGURATOR("view-offer-configurator");

	private final String controllerName;

	/**
	 * Constructor of the enum
	 *
	 * @param controllerName {@link String} that represent the controller name
	 */
	ControllerNames(String controllerName) {
		this.controllerName = controllerName;
	}

	@Override
	public String toString() {
		return this.controllerName;
	}
}
