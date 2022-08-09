package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.MeetUpdateDataEventFactory;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedListenerFor;
import com.barattoManager.ui.annotations.treeNodeSelectedListener.TreeNodeSelectedistenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.tree.event.ModelDataHasChangeListener;

import javax.swing.tree.DefaultMutableTreeNode;

public class MeetTreeController implements BaseController, ModelDataHasChangeListener {

	private final MeetTreeModel model;
	private final MeetTreeView view;

	public MeetTreeController(MeetTreeModel model, MeetTreeView view) {
		this.model = model;
		this.view = view;

		this.model.addModelDataHasChangeListener(this);
		MeetUpdateDataEventFactory.getEventHandler().addListener(model);

		this.view.drawTree(model.getCategories());

		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}


	@Override
	public MeetTreeModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@Override
	public void dataChange() {
		this.view.drawTree(model.getCategories());
		TreeNodeSelectedistenerInstaller.processAnnotations(this, view);
	}

	@TreeNodeSelectedListenerFor(sourceField = "tree")
	private void nodeSelectedChange() {
		Object lastSelectedPathComponent = view.getTree().getLastSelectedPathComponent();

		if (lastSelectedPathComponent != null)
			model.setTreeNodes(((DefaultMutableTreeNode) lastSelectedPathComponent).getPath());
	}

}
