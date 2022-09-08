package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.configurator.categoryEditor.CategoryEditorController;

import javax.swing.*;

/**
 * Abstract class used to represent a meet editor view <br>
 * Extends {@link ActionNotifierHandler} because it is used to handle the actions <br>
 * This class must be extended from all views of the {@link MeetEditorController}
 */
public abstract class MeetEditorView extends ActionNotifierHandler implements View {

	abstract void setMeetTree(JPanel treePanel);

}
