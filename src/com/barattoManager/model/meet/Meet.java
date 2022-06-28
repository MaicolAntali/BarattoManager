package com.barattoManager.model.meet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Meet {
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

	public Meet(String city, String square, DayOfWeek day, LocalTime startTime, LocalTime endTime, int daysBeforeExpire) {
		this.uuid =  UUID.randomUUID().toString();
		this.city = city;
		this.square = square;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userBookedMeetUuid = null;
		this.daysBeforeExpire = daysBeforeExpire;
		this.dateOfMeet = LocalDate.now().with(TemporalAdjusters.next(day));
		this.alreadyUpdated=false;
	}

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
		this.uuid = uuid;
		this.city = city;
		this.square = square;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userBookedMeetUuid = userBookedMeetUuid;
		this.daysBeforeExpire = daysBeforeExpire;
		this.dateOfMeet = dateOfMeet;
		this.alreadyUpdated=alreadyUpdated;
	}

	public void bookMeet(String userUuid) {
		if (getUserBookedMeetUuid().isEmpty()) {
			userBookedMeetUuid = userUuid;
		}
	}

	public String getUuid() {
		return uuid;
	}

	public String getCity() {
		return city;
	}

	public String getSquare() {
		return square;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public Optional<String> getUserBookedMeetUuid() {
		return Optional.ofNullable(userBookedMeetUuid);
	}

	public int getDaysBeforeExpire() {
		return daysBeforeExpire;
	}

	public LocalDate getDateOfMeet() {
		return dateOfMeet;
	}

	public boolean isAlreadyUpdated() {
		return alreadyUpdated;
	}

	public void setAlreadyUpdated(boolean alreadyUpdated) {
		this.alreadyUpdated = alreadyUpdated;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Meet meet = (Meet) o;
		if (hashCode() == meet.hashCode()) return true;
		return getCity().equals(meet.getCity())
				&& getSquare().equals(meet.getSquare())
				&& getDay() == meet.getDay()
				&& getDateOfMeet().isEqual(meet.getDateOfMeet())
				&& (!getStartTime().isAfter(meet.getEndTime()) ? getStartTime().equals(meet.getStartTime()) : !getStartTime().isAfter(meet.getEndTime()))
				&& (!meet.getStartTime().isAfter(getEndTime()) ? getEndTime().equals(meet.getEndTime()) : !meet.getStartTime().isAfter(getEndTime()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCity(), getSquare(), getDay(), getStartTime(), getEndTime(), getDateOfMeet());
	}
}
