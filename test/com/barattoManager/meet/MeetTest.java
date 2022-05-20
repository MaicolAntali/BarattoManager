package com.barattoManager.meet;

import com.barattoManager.exception.IllegalValuesException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetTest {

	@Test
	void simpleIntervalsTest() throws IllegalValuesException {
		// start time = 10:00am
		// end time = 11:00am
		var meet = new Meet("test", "test", new ArrayList<>(List.of("Monday")), 10*60, 11*60);
		var intervals = new ArrayList<>(List.of(LocalTime.of(10, 0), LocalTime.of(10, 30), LocalTime.of(11, 0)));

		assertEquals(intervals, meet.getIntervals());
	}

	@Test
	void complexIntervalsTest() throws IllegalValuesException {
		// start time = 10:10am
		// end time = 11:55am
		var meet = new Meet("test", "test", new ArrayList<>(List.of("Monday")), 10*60+10, 11*60+55);
		var intervals = new ArrayList<>(List.of(LocalTime.of(10, 10), LocalTime.of(10, 40), LocalTime.of(11, 10), LocalTime.of(11, 40)));

		assertEquals(intervals, meet.getIntervals());
	}

	@Test
	void equalsWithDifferenteTimeTest() throws IllegalValuesException {
		// meet2 is inside meet
		var meet = new Meet("test", "test", new ArrayList<>(List.of("Monday")), 10*60, 15*60);
		var meet2 = new Meet("test", "test", new ArrayList<>(List.of("Monday")), 11*60, 12*60+10);

		meet.equals(meet2);
		assertTrue(meet.equals(meet2));
	}

	@Test
	void checkTime() {
		assertThrows(IllegalValuesException.class, () -> new Meet("test", "test", new ArrayList<>(List.of("Monday")), 18*60, 10*60));
	}
}