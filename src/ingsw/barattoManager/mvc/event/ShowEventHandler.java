package ingsw.barattoManager.mvc.event;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class ShowEventHandler {

	private static final ArrayList<ShowEventListener> LISTENERS = new ArrayList<>();

	public static void addListeners(ShowEventListener showEventHandler){
		LISTENERS.add(showEventHandler);
	}

	public static void fireListener(Event event) {
		if (Objects.equals(event.eventName(), "AddNewPanel"))
			LISTENERS.forEach(listener -> listener.addNewPanel(
					(Component) event.params().get(0),
					(String) event.params().get(1))
			);
		else if (Objects.equals(event.eventName(), "ShowPanel"))
			LISTENERS.forEach(listener -> listener.showPanel(
					(String) event.params().get(0))
			);
	}
}
