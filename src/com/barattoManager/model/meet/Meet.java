package com.barattoManager.model.meet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Class representing the meet object
 */
public final class Meet {

	private static final String PRE_CONDITION_CITY_NAME_IS_BLANK = "Pre-condition: City name is blank";
	private static final String PRE_CONDITION_SQUARE_IS_BLANK = "Pre-condition: Square name is blank";
	private static final String PRE_CONDITION_DAYS_BEFORE_EXPIRE = "Pre-condition: The days before expire are < 0";
	private static final String PRE_CONDITION_START_TIME_IS_NULL = "Pre-condition: Start-time is null";
	private static final String PRE_CONDITION_END_TIME_IS_NULL = "Pre-condition: End-time is null";
	private static final String PRE_CONDITION_UUID_IS_BLANK = "Pre-condition: UUID is blank";
	private static final String PRE_CONDITION_DAY_IS_NULL = "Pre_condition: Day is null";
	private static final String PRE_CONDITION_DATE_OF_MEET_IS_NULL = "Pre_condition: Date of meet is null";

	@JsonProperty("uuid")
	private final String uuid;
	@JsonProperty("city")
	private final String city;
	@JsonProperty("square")
	private final String square;
	@JsonProperty("day")
	private final DayOfWeek day;
	@JsonProperty("start_time")
	private final LocalTime startTime;
	@JsonProperty("end_time")
	private final LocalTime endTime;
	@JsonProperty("user_booked_uuid")
	private String userBookedMeetUuid;
	@JsonProperty("day_before_expire")
	private int daysBeforeExpire;
	@JsonProperty("date_of_meet")
	private LocalDate dateOfMeet;
	@JsonProperty("already_updated")
	private boolean alreadyUpdated;

	/**
	 * Constructor of the class
	 *
	 * @param city             City of the meet
	 * @param square           Square of the meet
	 * @param day              {@link DayOfWeek} of the meet
	 * @param startTime        Start time of the meeting in minutes
	 * @param endTime          End time of the meeting in minutes
	 * @param daysBeforeExpire Number of days for which the meet is valid
	 */
	public Meet(String city, String square, DayOfWeek day, LocalTime startTime, LocalTime endTime, int daysBeforeExpire) {
		assert !city.isBlank() : PRE_CONDITION_CITY_NAME_IS_BLANK;
		assert !square.isBlank() : PRE_CONDITION_SQUARE_IS_BLANK;
		Objects.requireNonNull(startTime, PRE_CONDITION_START_TIME_IS_NULL);
		Objects.requireNonNull(endTime, PRE_CONDITION_END_TIME_IS_NULL);
		assert daysBeforeExpire >= 0 : PRE_CONDITION_DAYS_BEFORE_EXPIRE;

		this.uuid = UUID.randomUUID().toString();
		this.city = city;
		this.square = square;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userBookedMeetUuid = null;
		this.daysBeforeExpire = daysBeforeExpire;
		this.dateOfMeet = LocalDate.now().with(TemporalAdjusters.next(day));
		this.alreadyUpdated = false;
	}

	/**
	 * Constructor of the class
	 *
	 * @param uuid               UUID of the meet
	 * @param city               City of the meet
	 * @param square             Square of the meet
	 * @param day                {@link DayOfWeek} of the meet
	 * @param startTime          Start time of the meeting in minutes
	 * @param endTime            End time of the meeting in minutes
	 * @param userBookedMeetUuid Username of the user who booked the meet (can be null)
	 * @param daysBeforeExpire   Number of days for which the meet is valid
	 * @param dateOfMeet         Date of the meet
	 * @param alreadyUpdated     True if the date of meet is already updated
	 */
	public Meet(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("city") String city,
			@JsonProperty("square") String square,
			@JsonProperty("day") DayOfWeek day,
			@JsonProperty("start_time") LocalTime startTime,
			@JsonProperty("end_time") LocalTime endTime,
			@JsonProperty("user_booked_uuid") String userBookedMeetUuid,
			@JsonProperty("day_before_expire") int daysBeforeExpire,
			@JsonProperty("date_of_meet") LocalDate dateOfMeet,
			@JsonProperty("already_updated") boolean alreadyUpdated
	) {
		assert !uuid.isBlank() : PRE_CONDITION_UUID_IS_BLANK;
		assert !city.isBlank() : PRE_CONDITION_CITY_NAME_IS_BLANK;
		assert !square.isBlank() : PRE_CONDITION_SQUARE_IS_BLANK;
		Objects.requireNonNull(day, PRE_CONDITION_DAY_IS_NULL);
		Objects.requireNonNull(startTime, PRE_CONDITION_START_TIME_IS_NULL);
		Objects.requireNonNull(endTime, PRE_CONDITION_END_TIME_IS_NULL);
		assert daysBeforeExpire >= 0 : PRE_CONDITION_DAYS_BEFORE_EXPIRE;
		Objects.requireNonNull(dateOfMeet, PRE_CONDITION_DATE_OF_MEET_IS_NULL);

		this.uuid = uuid;
		this.city = city;
		this.square = square;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userBookedMeetUuid = userBookedMeetUuid;
		this.daysBeforeExpire = daysBeforeExpire;
		this.dateOfMeet = dateOfMeet;
		this.alreadyUpdated = alreadyUpdated;
	}

	/**
	 * Method used to book a {@link Meet}
	 *
	 * @param userUuid Username of the user booking the meet
	 */
	public void bookMeet(String userUuid) {
		if (getUserBookedMeetUuid().isEmpty()) {
			userBookedMeetUuid = userUuid;
		}
	}

	/**
	 * Method used to un-book a {@link Meet}
	 */
	public void unBookMeet() {
		userBookedMeetUuid = null;
	}

	/**
	 * Method used to return the uuid of the meet
	 *
	 * @return uuid of the meet
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the city of the meet
	 *
	 * @return City of the meet
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Method used to get the square of the meet
	 *
	 * @return square of the meet
	 */
	public String getSquare() {
		return square;
	}

	/**
	 * Method used to get the {@link DayOfWeek} of the meet
	 *
	 * @return {@link DayOfWeek} of the meet
	 */
	public DayOfWeek getDay() {
		return day;
	}

	/**
	 * Method used to get the start time of the meet
	 *
	 * @return {@link LocalTime} start time of the meet
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * Method used to get the end time of the meet
	 *
	 * @return {@link LocalTime} end time of the meet
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * Method used to get the user whose booked the meet (otherwise will return an empty {@link Optional})
	 *
	 * @return {@link Optional} with the username otherwise an empty optional
	 */
	public Optional<String> getUserBookedMeetUuid() {
		return Optional.ofNullable(userBookedMeetUuid);
	}

	/**
	 * Method used to get the day before expire of the meet
	 *
	 * @return day before expire of the meet
	 */
	public int getDaysBeforeExpire() {
		return daysBeforeExpire;
	}

	/**
	 * Method used to get the {@link LocalDate} of the meet
	 *
	 * @return {@link LocalDate} of the meet
	 */
	public LocalDate getDateOfMeet() {
		return dateOfMeet;
	}

	/**
	 * Method used to return if the meet is already updated true otherwise false
	 *
	 * @return if the meet is already updated true otherwise false
	 */
	public boolean isAlreadyUpdated() {
		return alreadyUpdated;
	}

	/**
	 * Method used to set if the meet is already updated true otherwise false
	 *
	 * @param alreadyUpdated True if the meet is already updated otherwise false
	 */
	public void setAlreadyUpdated(boolean alreadyUpdated) {
		this.alreadyUpdated = alreadyUpdated;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Meet meet = (Meet) o;
		if (hashCode() == meet.hashCode()) return true;
		return getCity().equalsIgnoreCase(meet.getCity())
				&& getSquare().equalsIgnoreCase(meet.getSquare())
				&& getDay() == meet.getDay()
				&& getDateOfMeet().isEqual(meet.getDateOfMeet())
				&& (
					(
						(getStartTime().isBefore(meet.getStartTime()))
						&& (getEndTime().isAfter(meet.getStartTime()))
					)
					||
					(
						(getEndTime().isAfter(meet.getEndTime()))
						&& (getStartTime().isBefore(meet.getEndTime()))
					)
				);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCity(), getSquare(), getDay(), getStartTime(), getEndTime(), getDateOfMeet());
	}
}
