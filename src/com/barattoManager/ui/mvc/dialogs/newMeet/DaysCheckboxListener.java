package com.barattoManager.ui.mvc.dialogs.newMeet;

/**
 * Interface that define the {@link #daysUpdate(String, boolean)} method which is used when the event of days selection has occurred
 */
public interface DaysCheckboxListener {

	/**
	 * Method used to update the day
	 *
	 * @param day      Selected/not selected day
	 * @param selected Is true if is selected otherwise false
	 */
	void daysUpdate(String day, boolean selected);

}
