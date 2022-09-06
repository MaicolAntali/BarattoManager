package com.barattoManager.ui.mvc.dialogs.select.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.Model;

import java.util.List;

public class SelectMeetModel implements Model {

	private final List<Meet> meets;

	private Meet meetSelected;

	public SelectMeetModel(List<Meet> meets) {
		this.meets = meets;
	}

	public List<Meet> getMeets() {
		return meets;
	}

	public Meet getMeetSelected() {
		return meetSelected;
	}

	public void setMeetSelected(Meet meetSelected) {
		this.meetSelected = meetSelected;
	}
}
