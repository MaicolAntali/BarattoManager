package com.barattoManager.meet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Meet {

	/**
	 * City of meet
	 */
	@JsonProperty("city")
	private final String city;
	/**
	 * Square of meet
	 */
	@JsonProperty("square")
	private final String square;
	/**
	 * Days of meet
	 */
	@JsonProperty("days")
	private final ArrayList<String> days;
	/**
	 * intervals of meet
	 */
	@JsonProperty("intervals")
	private final ArrayList<LocalTime> intervals;


	/**
	 * {@link Meet} constructor
	 *
	 * @param city      City of meeting
	 * @param square    Square of meeting
	 * @param days      Days of meeting, must be an {@link ArrayList}
	 * @param startTime Start hour of meetings, must be experienced in minutes <i>(10:00 -> 10*60)</i>
	 * @param endTime   End hour of meetings, must be experienced in minutes <i>(14:30 -> 10*60+30)</i>
	 */
	public Meet(String city, String square, ArrayList<String> days, int startTime, int endTime) {
		this.city = city;
		this.square = square;
		this.days = days;
		this.intervals = intervals(startTime, endTime);
	}

	/**
	 * {@link Meet} constructor
	 *
	 * @param city      City of meeting
	 * @param square    Square of meeting
	 * @param days      Days of meeting, must be an {@link ArrayList}
	 * @param intervals Intervals of time, must be an {@link ArrayList}
	 */
	public Meet(
			@JsonProperty("city") String city,
			@JsonProperty("square") String square,
			@JsonProperty("days") ArrayList<String> days,
			@JsonProperty("intervals") ArrayList<LocalTime> intervals
	) {
		this.city = city;
		this.square = square;
		this.days = days;
		this.intervals = intervals;
	}

	/**
	 * Method used to get the city of meet
	 *
	 * @return {@link #city}
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Method used to get the square of meet
	 *
	 * @return {@link #square}
	 */
	public String getSquare() {
		return square;
	}

	/**
	 * Method used to get the days of meet
	 *
	 * @return {@link #days}
	 */
	public ArrayList<String> getDays() {
		return days;
	}

	/**
	 * Method used to get the ArrayList of intervals
	 *
	 * @return {@link #intervals}
	 */
	public ArrayList<LocalTime> getIntervals() {
		return intervals;
	}

	/**
	 * Method used to compare two Meet objects
	 *
	 * @param meet Meet to compare
	 * @return True if are equals or have overlapping intervals otherwise false
	 * @see #intervalsOverlapping(Meet)
	 */
	public boolean equals(Meet meet) {
		if (this == meet) return true;
		if (meet == null || getClass() != meet.getClass()) return false;
		if (this.hashCode() != meet.hashCode()) return false;
		return Objects.equals(city, meet.city) && Objects.equals(square, meet.square) && Objects.equals(days, meet.days) && intervalsOverlapping(meet);
	}

	/**
	 * Method used to generate the hash code the object
	 *
	 * @return hashcode of a Meet object
	 */
	@Override
	public int hashCode() {
		// don’t hash the intervals because two objects might look different if they have overlapping intervals
		return Objects.hash(city, square, days);
	}

	/**
	 * Method used to create 30-minute intervals given a start and end time
	 *
	 * @param start Start hour of meetings, must be experienced in minutes <i>(10:00 -> 10*60)</i>
	 * @param end   End hour of meetings, must be experienced in minutes <i>(14:30 -> 10*60+30)</i>
	 * @return An {@link ArrayList} that contains 30-minute intervals
	 */
	private ArrayList<LocalTime> intervals(int start, int end) {
		ArrayList<LocalTime> tmpList = new ArrayList<>();

		int time = start;
		while (time <= end) {
			tmpList.add(LocalTime.of(time / 60, time % 60));
			time += 30;
		}

		return tmpList;
	}

	/**
	 * Method used to check if a time interval is within another. <br/>
	 * Uses the start and end times of the two intervals to verify that one of the two contains the other.<br/>
	 * Check that the start time is less than or equal to the other and also check that the end time is greater or equal to the other.<br/>
	 * Formula: {@code z <= y && a >= b || y <= z && b >= a}
	 *
	 * @param meet Meet Object to compare
	 * @return true if one of the two intervals is inside the other otherwise false
	 */
	private boolean intervalsOverlapping(Meet meet) {
		var innerStart = intervals.get(0);
		var innerEnd   = intervals.get(intervals.size() - 1);
		var meetStart  = meet.getIntervals().get(0);
		var meetEnd    = meet.getIntervals().get(meet.getIntervals().size() - 1);

		var innerStartMinutes = innerStart.getHour() * 60 + innerStart.getMinute();
		var innerEndMinutes   = innerEnd.getHour() * 60 + innerEnd.getMinute();
		var meetStartMinutes  = meetStart.getHour() * 60 + meetStart.getMinute();
		var meetEndMinutes    = meetEnd.getHour() * 60 + meetEnd.getMinute();

		return innerStartMinutes <= meetStartMinutes && innerEndMinutes >= meetEndMinutes || meetStartMinutes <= innerStartMinutes && meetEndMinutes >= innerEndMinutes;
	}
}
