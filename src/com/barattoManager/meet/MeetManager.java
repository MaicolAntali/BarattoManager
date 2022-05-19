package com.barattoManager.meet;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class MeetManager {

	/**
	 * User JSON file
	 */
	private final File meetFile = new File(AppConfigurator.getInstance().getFileName("meet_file"));
	/**
	 * {@link ObjectMapper} object, used to parse JSON
	 */
	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.build();

	/**
	 * {@link HashMap} meet map
	 */
	private final ArrayList<Meet> meetArrayList;

	private MeetManager() {
		if (meetFile.exists()) {
			try {
				meetArrayList = objectMapper.readValue(meetFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			meetArrayList = new ArrayList<>();
		}
	}

	/**
	 * Method used to create get the {@link MeetManager} instance.
	 * This method use the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code getInstance()} method is called.
	 * Also is thread safe cause every thread read the same {@link MeetManager} instance.
	 *
	 * @return The Instance of {@link MeetManager} class
	 */
	public static MeetManager getInstance() {
		return MeetManagerHolder.instance;
	}

	public void addNewMeet(String city, String square, ArrayList<String> days, int startTime, int endTime) throws IllegalValuesException, AlreadyExistException {
		if (!city.isBlank() || !square.isBlank() || !days.isEmpty()) {
			// create the new meet object
			var newMeet = new Meet(city, square, days, startTime, endTime);

			var equalityCheck = meetArrayList.stream()
					.filter(meet -> meet.equals(newMeet))
					.findFirst();

			if (equalityCheck.isEmpty()) {
				meetArrayList.add(newMeet);
				saveMeetMapChange();
			}
			else  {
				throw new AlreadyExistException("Il luogo di incontro che stai probando ad inserire esiste già");
			}
		}
		else {
			throw new IllegalValuesException("Uno o piu campi non sono erano vuoti.\nRiprovare");
		}

	}

	/**
	 * Method used to get the {@link #meetArrayList}
	 *
	 * @return Map that contains each meet
	 */
	public ArrayList<Meet> getMeetArrayList() {
		return meetArrayList;
	}

	/**
	 * Holder class of instance
	 */
	private static final class MeetManagerHolder {
		/**
		 * Instance of {@link MeetManager}
		 */
		private static final MeetManager instance = new MeetManager();
	}

	/**
	 * Method used to save the {@link #meetArrayList} changes in the Json file({@link #meetFile})
	 */
	private void saveMeetMapChange() {
		try {
			objectMapper.writeValue(meetFile, meetArrayList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
