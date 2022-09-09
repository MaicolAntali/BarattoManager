package com.barattoManager.ui.utils;

/**
 * Enum used to represent the controller names
 */
public enum ControllerNames {

	/**
	 * {@link com.barattoManager.ui.mvc.homepage.HomepageController}
	 */
	HOMEPAGE("homepage"),
	/**
	 * {@link com.barattoManager.ui.mvc.login.LoginController}
	 */
	LOGIN("login"),

	/**
	 * {@link com.barattoManager.ui.mvc.viewer.homepage.ViewerHomepageController}
	 */
	HOMEPAGE_VIEWER("homepage-viewer"),
	/**
	 * {@link com.barattoManager.ui.mvc.register.RegisterController}
	 */
	REGISTER_VIEWER("register-viewer"),
	/**
	 * {@link com.barattoManager.ui.mvc.viewer.categoryViewer.ViewerCategoryController}
	 */
	CATEGORY_VIEWER("category-view"),
	/**
	 * {@link com.barattoManager.ui.mvc.viewer.meetViewer.ViewerMeetController}
	 */
	MEET_VIEWER("meet-view"),
	/**
	 * {@link com.barattoManager.ui.mvc.viewer.yourArticleViewer.ViewerYourArticleController}
	 */
	ARTICLE_VIEWER("article-viewer"),
	/**
	 * {@link com.barattoManager.ui.mvc.viewer.storeArticleViewer.ViewerStoreArticleController}
	 */
	STORE_VIEWER("store-viewer"),
	/**
	 * {@link com.barattoManager.ui.mvc.viewer.tradesViewer.ViewerTradesController}
	 */
	TRADES_VIEWER("trades-viewer"),

	/**
	 * {@link com.barattoManager.ui.mvc.configurator.homepage.ConfiguratorHomepageController}
	 */
	HOMEPAGE_CONFIGURATOR("homepage-configurator"),
	/**
	 * {@link com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorController}
	 */
	CATEGORY_EDITOR("category-editor"),
	/**
	 * {@link com.barattoManager.ui.mvc.configurator.meetEditor.MeetEditorController}
	 */
	MEET_EDITOR("meet-editor"),
	/**
	 * {@link com.barattoManager.ui.mvc.register.RegisterController}
	 */
	REGISTER_CONFIGURATOR("register-configurator"),
	/**
	 * {@link com.barattoManager.ui.mvc.configurator.offerView.ViewOfferController}
	 */
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
