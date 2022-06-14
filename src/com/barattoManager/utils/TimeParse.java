package com.barattoManager.utils;

import com.barattoManager.exception.IllegalValuesException;

/**
 * Utility class of time
 */
public final class TimeParse {

	/**
	 * Error: the time format is not correct, please use the correct format (HH:MM)
	 */
	private static final String ERROR_TIME_FORMAT = "L'orario inserito non è valido.\nPer favore rispettare il formato: HH:MM";
	/**
	 * Error: the time is out of time-range (the hour mustn't be >= 24 and the minutes >=60)
	 */
	private static final String ERROR_TIME_OUT_OF_TIME_RANGE = "L'orario inserito non è valido.\nL'ora non deve essere >= di 24 e i minuti non possono essere >= 60";

	/**
	 * Parse a time string into an int that represent the time in minutes
	 * @param hour String that represent the hour
	 * @param minute String that represent the minutes
	 * @return int that represent the time in minutes
	 * @throws IllegalValuesException Is thrown if the time format or range are incorrect
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

		if (hourInt >= 24 || minuteInt >= 60) {
			throw new IllegalValuesException(ERROR_TIME_OUT_OF_TIME_RANGE);
		}


		return  hourInt * 60 + minuteInt;
	}

	/**
	 * Parse a time into an int that represent the time in minutes
	 * @param hour int that represent the hour
	 * @param minute int that represent the minutes
	 * @return int that represent the time in minutes
	 */
	public static int hourToMinuteTime(int hour, int minute) {
		return  hour * 60 + minute;
	}

	/**
	 * Parse a time string into an int that represent that time in minutes
	 * @param time String that represent the time. Must be in the format => HH:MM
	 * @return  int that represent that time in minutes
	 * @throws IllegalValuesException Is thrown if the time format is incorrect
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
