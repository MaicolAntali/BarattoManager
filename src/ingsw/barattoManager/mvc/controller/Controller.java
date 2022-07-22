package ingsw.barattoManager.mvc.controller;

import ingsw.barattoManager.mvc.event.BaseEventListeners;
import ingsw.barattoManager.mvc.event.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class Controller implements ActionListener, BaseEventListeners {
	private final HashMap<String, ActionRunner> actionsMap;

	public Controller() {
		this.actionsMap = new HashMap<>();
	}

	public void registerAction(String actionName, ActionRunner function) {
		actionsMap.put(actionName, function);
	}

	public void registerAction(JButton jButton, ActionRunner function) {
		jButton.addActionListener(this);
		actionsMap.put(jButton.getActionCommand(), function);
	}

	public ActionRunner getAction(String actionName) {
		return actionsMap.get(actionName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getAction(e.getActionCommand()).run();
	}

	@Override
	public void eventPerformed(Event event) {
		getAction(event.eventName()).run();
	}
}
