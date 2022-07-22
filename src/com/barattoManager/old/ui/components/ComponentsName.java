package com.barattoManager.old.ui.components;

import com.barattoManager.old.ui.components.configurator.ConfiguratorCategoryEditorUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorHomeUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorMeetEditorUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorOfferView;
import com.barattoManager.old.ui.components.viewer.*;
import ingsw.barattoManager.mvc.application.homepage.HomepageUi;
import ingsw.barattoManager.mvc.application.login.LoginUi;

/**
 * Enum that represent the views name
 */
public enum ComponentsName {
    /**
     * {@link HomepageUi}
     */
    INITIAL_PANEL("init"),
    /**
     * {@link LoginUi}
     */
    LOGIN("login"),
    /**
     * {@link ConfiguratorHomeUi}
     */
    CONF_HOME("conf_home"),
    /**
     * {@link ConfiguratorCategoryEditorUi}
     */
    CONF_CATEGORY_EDITOR("conf_category_editor"),
    /**
     * {@link ConfiguratorMeetEditorUi}
     */
    CONF_MEET_EDITOR("conf_meet_editor"),
    /**
     * {@link ConfiguratorOfferView}
     */
    CONF_OFFER_VIEW("conf_offer_view"),
    /**
     * {@link ConfiguratorHomeUi}
     */
    VIEWER_HOME("viewer_home"),
    /**
     * {@link ViewerCategoryViewUi}
     */
    VIEWER_CATEGORY("viewer_category"),
    /**
     * {@link ViewerMeetViewUi}
     */
    VIEWER_MEET("viewer_meet"),
    /**
     * {@link ViewerDashboardArticle}
     */
    VIEWER_YOUR_ARTICLES("viewer_your_article"),
    /**
     * {@link ViewerStoreArticle}
     */
    VIEWER_STORE_ARTICLES("viewer_store_article"),

    /**
     * {@link ViewerExchangesViewUi}
     */
    VIEWER_EXCHANGES("viewer_exchanges_view");



    private final String name;

    /**
     * Constructor of the class
     *
     * @param name name of the view
     */
    ComponentsName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
