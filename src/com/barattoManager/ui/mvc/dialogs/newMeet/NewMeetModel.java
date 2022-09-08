package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.login.LoginController;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Model of {@link NewMeetController} that contains the data
 */
public class NewMeetModel implements Model {

	private String city;
	private String square;
	private final ArrayList<String> days;
	private String startTime;
	private String endTime;
	private String daysBeforeExpire;

	/**
	 * Constructor of the class
	 */
	public NewMeetModel() {
		this.city = "";
		this.square = "";
		this.days = new ArrayList<>();
		this.startTime = "";
		this.endTime = "";
		this.daysBeforeExpire = "";
	}

	/**
	 * Method used to get the city of the meet
	 * @return City of the meet
	 */
	public String getCity() {
		return city;
	}

	/**
	 *  Method used to set the city of the meet as a {@link String}
	 * @param city {@link String} to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Method used to get the square of the meet
	 * @return square of the meet
	 */
	public String getSquare() {
		return square;
	}

	/**
	 * Method used to set the square of the meet as a {@link String}
	 * @param square {@link String} to set
	 */
	public void setSquare(String square) {
		this.square = square;
	}

	/**
	 * Method used to return an {@link ArrayList} that contains the days
	 * @return {@link ArrayList} that contains the days
	 */
	public ArrayList<String> getDays() {
		return days;
	}

	/**
	 * Method used to get the start time of the meet
	 * @return {@link LocalTime} start time of the meet
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Method used to set the start time of the meet as a {@link String}
	 * @param startTime {@link String} to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * Method used to get the end time of the meet
	 * @return {@link LocalTime} end time of the meet
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Method used to set the end time of the meet as a {@link String}
	 * @param endTime {@link String} to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * Method used to get the day before expire of the meet
	 * @return day before expire of the meet
	 */
	public String getDaysBeforeExpire() {
		return daysBeforeExpire;
	}

	/**
	 * Method used to set the day before expire of the meet
	 * @param daysBeforeExpire {@link String} to set
	 */
	public void setDaysBeforeExpire(String daysBeforeExpire) {
		this.daysBeforeExpire = daysBeforeExpire;
	}
}
