package com.barattoManager.utils.parser;

import java.time.DayOfWeek;
import java.util.HashMap;

public final class DateParser {
	private final static HashMap<String, DayOfWeek> dayHashMap = new HashMap<>() {{
		put("LUNEDI", DayOfWeek.MONDAY);
		put("MARTEDI", DayOfWeek.TUESDAY);
		put("MERCOLEDI", DayOfWeek.THURSDAY);
		put("GIOVEDI", DayOfWeek.WEDNESDAY);
		put("VENERDI", DayOfWeek.FRIDAY);
		put("SABATO", DayOfWeek.SATURDAY);
		put("DOMENICA", DayOfWeek.SUNDAY);
	}};


	public static DayOfWeek stringToWeekDay(String day) {
		return dayHashMap.get(day);
	}
}
