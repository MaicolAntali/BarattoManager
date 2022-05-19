package com.barattoManager.utils;

import com.barattoManager.exception.IllegalValuesException;

public final class TimeParse {

	public static final String ERROR_TIME_FORMAT = "Impossibile leggere un orario valido.\nPer favore rispetta il formato: HH:MM";

	/**
	 * Parse a time string into an int that rappresenta that time in minutes
	 * @param hour String that represent the hour
	 * @param minute String that represent the minuts
	 * @return int that represent that time in minutes
	 */
	public static int hourToMinuteTime(String hour, String minute) throws IllegalValuesException {
		var hourInt = 0;
		var minuteInt = 0;

		try {
			hourInt = Integer.parseInt(hour);
			minuteInt = Integer.parseInt(minute);
		}
		catch (NumberFormatException ex) {
			throw new IllegalValuesException(ERROR_TIME_FORMAT);
		}

		return  hourInt * 60 + minuteInt;
	}

	/**
	 * Parse a time string into an int that represent that time in minutes
	 * @param time String that rappresenta the time. Must be in the format => HH:MM
	 * @return  int that represent that time in minutes
	 */
	public static int hourToMinuteTime(String time) throws IllegalValuesException {
		var split = time.split(":");

		try {
			return hourToMinuteTime(split[0], split[1]);
		}
		catch (IndexOutOfBoundsException ex) {
			throw new IllegalValuesException(ERROR_TIME_FORMAT);
		}
	}
}
