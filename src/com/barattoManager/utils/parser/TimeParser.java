package com.barattoManager.utils.parser;

import com.barattoManager.exception.InvalidArgumentException;

public class TimeParser {

	private static final String ERROR_TIME_FORMAT = "L'orario inserito non è valido.\nPer favore rispettare il formato: HH:MM";
	private static final String ERROR_TIME_OUT_OF_TIME_RANGE = "L'orario inserito non è valido.\nL'ora non deve essere >= di 24 e i minuti non possono essere >= 60";

	/**
	 * Convert strings to a time in minutes
	 *
	 * @param hour   Hour string
	 * @param minute Minutes string
	 * @return time in minutes
	 * @throws InvalidArgumentException Is 1 if the time format or range are incorrect
	 */
	public static int hourToMinuteTime(String hour, String minute) throws InvalidArgumentException {
		var hourInt   = 0;
		var minuteInt = 0;

		try {
			hourInt = Integer.parseInt(hour);
			minuteInt = Integer.parseInt(minute);
		} catch (NumberFormatException ex) {
			throw new InvalidArgumentException(ERROR_TIME_FORMAT);
		}

		if (hourInt >= 24 || minuteInt >= 60) {
			throw new InvalidArgumentException(ERROR_TIME_OUT_OF_TIME_RANGE);
		}


		return hourInt * 60 + minuteInt;
	}

	/**
	 * Convert int to a time in minutes
	 *
	 * @param hour   Hour int
	 * @param minute Minutes int
	 * @return time in minutes
	 */
	public static int hourToMinuteTime(int hour, int minute) {
		return hour * 60 + minute;
	}

	/**
	 * Convert string to a time in minutes
	 *
	 * @param time Time string. <b>Must be in the format => HH:MM</b>
	 * @return time in minutes
	 * @throws InvalidArgumentException Is thrown if the time format is incorrect
	 */
	public static int hourToMinuteTime(String time) throws InvalidArgumentException {
		var split = time.split(":");

		try {
			return hourToMinuteTime(split[0], split[1]);
		} catch (IndexOutOfBoundsException ex) {
			throw new InvalidArgumentException(ERROR_TIME_FORMAT);
		}
	}
}
