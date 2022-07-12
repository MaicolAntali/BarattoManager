package com.barattoManager.utils.parser;

import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * Utility class of date
 */
public final class DateParser {
	/**
	 * {@link HashMap} of days (of the week)
	 */
	private final static HashMap<String, DayOfWeek> dayHashMap = new HashMap<>() {{
		put("LUNEDI", DayOfWeek.MONDAY);
		put("MARTEDI", DayOfWeek.TUESDAY);
		put("MERCOLEDI", DayOfWeek.THURSDAY);
		put("GIOVEDI", DayOfWeek.WEDNESDAY);
		put("VENERDI", DayOfWeek.FRIDAY);
		put("SABATO", DayOfWeek.SATURDAY);
		put("DOMENICA", DayOfWeek.SUNDAY);
	}};


	/**
	 * Method used to get the day of the week as a String
	 * @param day Day of the week
	 * @return A day of the week as a String
	 */
	public static DayOfWeek stringToWeekDay(String day) {
		return dayHashMap.get(day);
	}
}
