package com.barattoManager.ui.mvc.dialogs.select.selectMeet;

import com.barattoManager.services.meet.Meet;
import com.barattoManager.ui.mvc.Model;

import java.util.List;

/**
 * Model of {@link SelectMeetController} that contains the data
 */
public class SelectMeetModel implements Model {

	private final List<Meet> meets;

	private Meet meetSelected;

	/**
	 * Constructor of the class
	 *
	 * @param meets {@link List} of {@link Meet}
	 */
	public SelectMeetModel(List<Meet> meets) {
		this.meets = meets;
	}

	/**
	 * Method used to get the meets
	 *
	 * @return {@link List} of meets
	 */
	public List<Meet> getMeets() {
		return meets;
	}

	/**
	 * Method used to get the selected meet
	 *
	 * @return selected {@link Meet}
	 */
	public Meet getMeetSelected() {
		return meetSelected;
	}

	/**
	 * Method used to set the selected meet
	 *
	 * @param meetSelected Selected {@link Meet}
	 */
	public void setMeetSelected(Meet meetSelected) {
		this.meetSelected = meetSelected;
	}
}
