package com.barattoManager.ui.mvc;

import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.action.event.ActionNotifierListener;

import java.util.HashMap;

/**
 * Abstract class that represent a Grasp controller
 */
public abstract class GraspController implements Controller, ActionNotifierListener {

	private final HashMap<String, Action> actionHashMap;

	/**
	 * Constructor of the class
	 */
	public GraspController() {
		actionHashMap = new HashMap<>();
	}

	protected abstract void initAction();

	/**
	 * Method used to add an {@link Action} to the {@link HashMap}
	 * @param actionName {@link String} Key of the {@link HashMap}
	 * @param action {@link Action} Value of the {@link HashMap}
	 */
	public void addAction(String actionName, Action action) {
		actionHashMap.put(actionName, action);
	}


	@Override
	public void notify(String actionName) {
		var action = actionHashMap.get(actionName);

		if (action == null)
			System.out.printf("Missing action in hashmap: %s%n", actionName);
		else
			action.run();
	}
}

