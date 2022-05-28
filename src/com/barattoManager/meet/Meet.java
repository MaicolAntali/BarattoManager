package com.barattoManager.meet;

import com.barattoManager.exception.IllegalValuesException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represent a meet
 */
public class Meet {
	/**
	 * Pre-condition: City name is blank
	 */
	private static final String PRE_CONDITION_CITY_NAME_IS_BLANK = "Pre-condition: City name is blank";
	/**
	 * Pre-condition: Square name is blank
	 */
	private static final String PRE_CONDITION_SQUARE_IS_BLANK = "Pre-condition: Square name is blank";
	/**
	 * Pre-condition: Days is empty
	 */
	private static final String PRE_CONDITION_DAYS_IS_BLANK = "Pre-condition: Days is empty";
	/**
	 * Pre-condition: Pre-condition: Start time < 0
	 */
	private static final String PRE_CONDITION_START_TIME_NAME_IS_BLANK = "Pre-condition: start time < 0";
	/**
	 * Pre-condition: Pre-condition: End time < 0
	 */
	private static final String PRE_CONDITION_END_TIME_NAME_IS_BLANK = "Pre-condition: end time < 0";
	/**
	 * Post-condition: The Arraylist of days is not empty
	 */
	private static final String POST_CONDITION_DAYS_ARRAYLIST_IS_EMPTY = "Post-condition: The Arraylist of days is empty";
	/**
	 * Post-condition: The Arraylist of intervals is empty
	 */
	private static final String POST_CONDITION_INTERVALS_ARRAYLIST_IS_EMPTY = "Post-condition: The Arraylist of intervals is empty";

	/**
	 * Invalid time input error, the start hour be less than or equal to the end hour
	 */
	private static final String INVALID_TIME_INPUT = "Il valore temporale inserito è sbagliato.\n(Orario di inizio deve essere minore dell'orario di fine)";
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
	 * Intervals of meet
	 */
	@JsonProperty("intervals")
	private final ArrayList<LocalTime> intervals;


	/**
	 * {@link Meet} constructor
	 *
	 * @param city      City of meeting
	 * @param square    Square of meeting
	 * @param days      Days of meeting, must be an {@link ArrayList}
	 * @param startTime Start hour of meetings, must be expressed in minutes <i>(10:00 -> 10*60)</i>
	 * @param endTime   End hour of meetings, must be expressed in minutes <i>(14:30 -> 10*60+30)</i>
	 * @throws IllegalValuesException Is thrown if there are an illegal value
	 */
	public Meet(String city, String square, ArrayList<String> days, int startTime, int endTime) throws IllegalValuesException {
		assert !city.isBlank() : PRE_CONDITION_CITY_NAME_IS_BLANK;
		assert !square.isBlank(): PRE_CONDITION_SQUARE_IS_BLANK;
		assert !days.isEmpty(): PRE_CONDITION_DAYS_IS_BLANK;
		assert startTime >= 0 : PRE_CONDITION_START_TIME_NAME_IS_BLANK;
		assert endTime >= 0 : PRE_CONDITION_END_TIME_NAME_IS_BLANK;


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
		assert !city.isBlank() : PRE_CONDITION_CITY_NAME_IS_BLANK;
		assert !square.isBlank(): PRE_CONDITION_SQUARE_IS_BLANK;

		this.city = city;
		this.square = square;
		this.days = days;
		this.intervals = intervals;

		assert days.isEmpty() : POST_CONDITION_DAYS_ARRAYLIST_IS_EMPTY;
		assert intervals.isEmpty() : POST_CONDITION_INTERVALS_ARRAYLIST_IS_EMPTY;
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
		return Objects.equals(city, meet.city) &&
						Objects.equals(square, meet.square) &&
						(days.containsAll(meet.days) || meet.days.containsAll(days)) &&
						intervalsOverlapping(meet);
	}

	/**
	 * Method used to create 30-minute intervals given a start and end time
	 *
	 * @param start Start hour of meetings, must be expressed in minutes <i>(10:00 -> 10*60)</i>
	 * @param end   End hour of meetings, must be expressed in minutes <i>(14:30 -> 10*60+30)</i>
	 * @return An {@link ArrayList} that contains 30-minute intervals
	 * @throws IllegalValuesException Is thrown if the time is invalid
	 */
	private ArrayList<LocalTime> intervals(int start, int end) throws IllegalValuesException {
		if (start <= end) {
			ArrayList<LocalTime> tmpList = new ArrayList<>();

			int time = start;
			while (time <= end) {
				tmpList.add(LocalTime.of(time / 60, time % 60));
				time += 30;
			}

			return tmpList;
		}
		else {
			throw new IllegalValuesException(INVALID_TIME_INPUT);
		}
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

		return !innerStart.isAfter(meetEnd) && !meetStart.isAfter(innerEnd);
	}
}
