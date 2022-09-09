package com.barattoManager.ui.mvc.tree.meet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.services.meet.MeetUpdateDataEventFactory;
import com.barattoManager.ui.mvc.tree.TreeController;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeModel;
import com.barattoManager.ui.mvc.tree.category.CategoryTreeView;

/**
 * Controller that handle the events of the {@link MeetTreeView} and update the data in the {@link MeetTreeModel}
 */
public class MeetTreeController extends TreeController<Meet> {

	/**
	 * Constructor of the class
	 *
	 * @param model {@link MeetTreeModel} represent the model of the controller
	 * @param view {@link  MeetTreeView} represent the view of the controller
	 */
	public MeetTreeController(MeetTreeModel model, MeetTreeView view) {
		super(model, view);

		getModel().addModelDataHasChangeListener(this);
		MeetUpdateDataEventFactory.getEventHandler().addListener(model);

	}
}
