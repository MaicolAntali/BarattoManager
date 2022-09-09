package com.barattoManager.utils.parser;

import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * Class used to parte a {@link String} into a {@link DayOfWeek} object
 */
public class DateParser {
	private final static HashMap<String, DayOfWeek> dayHashMap = new HashMap<>() {{
		put("LUNEDI", DayOfWeek.MONDAY);
		put("MARTEDI", DayOfWeek.TUESDAY);
		put("MERCOLEDI", DayOfWeek.WEDNESDAY);
		put("GIOVEDI", DayOfWeek.THURSDAY);
		put("VENERDI", DayOfWeek.FRIDAY);
		put("SABATO", DayOfWeek.SATURDAY);
		put("DOMENICA", DayOfWeek.SUNDAY);
	}};

	/**
	 * Method used to associate a string to a {@link DayOfWeek}
	 *
	 * @param day String with the name of the day of the week
	 * @return {@link DayOfWeek} object
	 */
	public static DayOfWeek stringToWeekDay(String day) {
		return dayHashMap.get(day.replace('ì', 'i').toUpperCase());
	}
}
