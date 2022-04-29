package com.barattoManager.ui.components;

public enum ComponentsName {
    INITIAL_PANEL("init"),
    LOGIN("login"),
    CONF_HOME("conf_home"),
    CONF_CATEGORY_EDITOR("conf_category_editor");

    private final String name;

    ComponentsName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
