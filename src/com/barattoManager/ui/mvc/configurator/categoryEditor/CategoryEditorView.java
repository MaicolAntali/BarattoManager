package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * Abstract class used to represent a category editor view <br>
 * Extends {@link ActionNotifierHandler} because it is used to handle the actions <br>
 * This class must be extended from all views of the {@link CategoryEditorController}
 */
public abstract class CategoryEditorView extends ActionNotifierHandler implements View {

	abstract void setCategoryTree(JPanel treePanel);

}
