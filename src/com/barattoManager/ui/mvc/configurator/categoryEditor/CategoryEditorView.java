package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public abstract class CategoryEditorView extends ActionNotifierHandler implements View {

	abstract void setCategoryTree(JPanel treePanel);

}
