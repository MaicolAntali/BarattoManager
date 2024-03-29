package com.barattoManager.utils.parser;

import com.barattoManager.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeParserTest {

	@Test
	void hourToMinutesEasy() {
		assertEquals(600, TimeParser.hourToMinuteTime(10, 0));
	}

	@Test
	void hourToMinutesHard() {
		assertEquals(630, TimeParser.hourToMinuteTime(10, 30));
	}

	@Test
	void stringHourToMinutesEasy() throws InvalidArgumentException {
		assertEquals(600, TimeParser.hourToMinuteTime("10:00"));
	}

	@Test
	void stringHourToMinutesHard() throws InvalidArgumentException {
		assertEquals(630, TimeParser.hourToMinuteTime("10:30"));
	}

	@Test
	void stringHourToMinutesImpossible() {
		assertThrows(InvalidArgumentException.class, () -> TimeParser.hourToMinuteTime("10.00"));
	}

	@Test
	void illegalStringFormat() {
		assertThrows(InvalidArgumentException.class, () -> TimeParser.hourToMinuteTime("ab", "cd"));
	}

	@Test
	void moreThat24Hours() {
		assertThrows(InvalidArgumentException.class, () -> TimeParser.hourToMinuteTime("25", "00"));
	}

}