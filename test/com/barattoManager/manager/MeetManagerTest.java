package com.barattoManager.manager;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.meet.Meet;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MeetManagerTest {

	/*
	 *
	 * Se solo alcuni test falliscono provare ad aggiornare nel json l'attributo: date_of_meet
	 * Probabilmente la data è nel passato. Aggiornarla al lunedì successivo.
	 */

	private static MeetManager meetManagerInstance;

	@BeforeAll
	static void setUp() {
		meetManagerInstance = new MeetManager(
				new JsonHandler<String, Meet>(
						"test/json/meets.json",
						JsonMapper.builder()
								.addModule(new ParameterNamesModule())
								.addModule(new Jdk8Module())
								.addModule(new JavaTimeModule())
								.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
								.build()
				).readJson(String.class, Meet.class)
		);
	}

	@Test
	@Order(1)
	void getAvailableMeet() {
		assertEquals(5, meetManagerInstance.getAvailableMeet().size());
	}

	@Test
	@Order(1)
	void removeMeet() {
		meetManagerInstance.removeMeetByUuid("1a3a9572-faab-4240-911f-f1803345c7b9");
		assertEquals(5, meetManagerInstance.getMeets().size());
	}

	@Test
	@Order(2)
	void bookMeet() {
		meetManagerInstance.bookMeet("2364a7b8-6199-41e0-8057-88c4ac232f4f", "BOOKED_BY_TEST");

		assertEquals(
				"BOOKED_BY_TEST",
				meetManagerInstance.getMeetByUuid("2364a7b8-6199-41e0-8057-88c4ac232f4f")
						.orElseThrow()
						.getUserBookedMeetUuid()
						.orElseThrow()
		);
	}

	@Test
	@Order(3)
	void unBookMeet() {
		meetManagerInstance.unBookMeet("2364a7b8-6199-41e0-8057-88c4ac232f4f");

		assertEquals(
				Optional.empty(),
				meetManagerInstance.getMeetByUuid("2364a7b8-6199-41e0-8057-88c4ac232f4f")
						.orElseThrow()
						.getUserBookedMeetUuid()
		);
	}

	@Test
	void addNewMeetWithoutCity() {
		assertThrows(IllegalValuesException.class, () -> meetManagerInstance.addNewMeet("", "...", DayOfWeek.MONDAY, 100, 200, 1));
	}

	@Test
	void addNewMeetWithoutSquare() {
		assertThrows(IllegalValuesException.class, () -> meetManagerInstance.addNewMeet("...", "", DayOfWeek.MONDAY, 100, 200, 1));
	}

	@Test
	void addNewMeetWrongIntervals() {
		assertThrows(IllegalValuesException.class, () -> meetManagerInstance.addNewMeet("...", "...", DayOfWeek.MONDAY, 200, 100, 1));
	}

	@Test
	void addNewMeetDayBeforeExpireZero() {
		assertThrows(IllegalValuesException.class, () -> meetManagerInstance.addNewMeet("...", "...", DayOfWeek.MONDAY, 100, 200, 0));
	}

	@Test
	void addExistingMeetEasy() {
		assertThrows(AlreadyExistException.class, () -> meetManagerInstance.addNewMeet("Bergamo", "Stazione", DayOfWeek.MONDAY, 780, 810, 1));
	}

	@Test
	void addExistingMeetHard() {
		assertThrows(AlreadyExistException.class, () -> meetManagerInstance.addNewMeet("Bergamo", "Stazione", DayOfWeek.MONDAY, 790, 820, 1));
	}

	@Test
	void addExistingMeetHardV2() {
		assertThrows(AlreadyExistException.class, () -> meetManagerInstance.addNewMeet("BERgaMO", "staZIOne", DayOfWeek.MONDAY, 770, 800, 1));
	}

	@Test
	void addMeetWithoutDays() {
		assertThrows(IllegalValuesException.class, () -> meetManagerInstance.addNewMeet("...", "...", new ArrayList<>(), 770, 800, 1));
	}


	@Test
	void addNewMeet() {
		try {
			meetManagerInstance.addNewMeet("Brescia", "Piazza", DayOfWeek.MONDAY, 770, 800, 1);
		} catch (AlreadyExistException | IllegalValuesException e) {
			throw new RuntimeException(e);
		}

		assertEquals(6, meetManagerInstance.getMeets().size());
	}
}