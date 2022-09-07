package com.barattoManager.utils.parser;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DateParserTest {

	@Test
	void notValidDau() {
		assertNull(DateParser.stringToWeekDay("not_valid_day"));
	}

	@Test
	void monday() {
		assertEquals(DayOfWeek.MONDAY, DateParser.stringToWeekDay("lunedì"));
	}

	@Test
	void thursday() {
		assertEquals(DayOfWeek.THURSDAY, DateParser.stringToWeekDay("mercoledì"));
	}

	@Test
	void friday() {
		assertEquals(DayOfWeek.FRIDAY, DateParser.stringToWeekDay("venerdì"));
	}

	@Test
	void sunday() {
		assertEquals(DayOfWeek.SUNDAY, DateParser.stringToWeekDay("domenica"));
	}

}