package ingsw.barattoManager.mvc.event;

import java.util.ArrayList;

public record Event(String eventName, ArrayList<Object> params) {

	public Event(String eventName) {
		this(eventName, null);
	}

	public Event(String eventName, ArrayList<Object> params) {
		this.eventName = eventName;
		this.params = params;
	}
}

