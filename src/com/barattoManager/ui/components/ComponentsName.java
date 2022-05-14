package com.barattoManager.ui.components;

/**
 * Enum that represent the views name
 */
public enum ComponentsName {
    /**
     * {@link InitialMenuUI}
     */
    INITIAL_PANEL("init"),
    /**
     * {@link LoginUI}
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
     * {@link ConfiguratorHomeUi}
     */
    VIEWER_HOME("viewer_home");


    /**
     * view label
     */
    private final String name;

    /**
     * {@link ComponentsName} constructor
     * @param name name of view
     */
    ComponentsName(String name) {
        this.name = name;
    }

    /**
     * Method used to get the name of view
     * @return Name of view
     */
    @Override
    public String toString() {
        return this.name;
    }
}
