package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.ui.action.event.ActionNotifierHandler;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public abstract class MeetEditorView extends ActionNotifierHandler implements View {

	abstract void setMeetTree(JPanel treePanel);

}
