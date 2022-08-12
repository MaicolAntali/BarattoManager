package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.services.meet.MeetUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;

public class MeetTreeController extends TreeController<Meet> {

	public MeetTreeController(MeetTreeModel model, MeetTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		MeetUpdateDataEventFactory.getEventHandler().addListener(model);

	}
}
