package com.barattoManager.meet;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.utils.DateParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

public final class MeetManager {

	private final File jsonFile = new File(AppConfigurator.getInstance().getFileName("meet_file"));

	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();

	private final HashMap<String, Meet> meetHashMap;
	private MeetUpdaterDaemon meetUpdaterDaemon;
	private Thread deamonThread;

	private MeetManager() {
		if (jsonFile.exists()) {
			try {
				meetHashMap = objectMapper.readValue(jsonFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			meetHashMap = new HashMap<>();
		}

		this.meetUpdaterDaemon = new MeetUpdaterDaemon(this.meetHashMap);
	}

	private static final class MeetManagerHolder {
		private static final MeetManager instance = new MeetManager();
	}

	public static MeetManager getInstance() {
		return MeetManagerHolder.instance;
	}

	public void runUpdaterDaemon() {
		if (deamonThread == null || !deamonThread.isAlive()) {
			deamonThread = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							meetUpdaterDaemon,
							0,
							60*1_000 // 1 Minutes
					)
			);
			deamonThread.start();
		}
	}

	public void addNewMeet(String city, String square, DayOfWeek day, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		var intervals = generateIntervals(intervalStartTime, intervalEndTime);

		var meets = new ArrayList<Meet>();
		IntStream.range(0, intervals.size()-1)
				.forEach(i -> meets.add(new Meet(city, square, day, intervals.get(i), intervals.get(i+1), daysBeforeExpire)));


		var notUniqueMeet = new ArrayList<Meet>();
		meets.forEach(meet -> {
			var isMeetUnique = meetHashMap.values().stream()
					.noneMatch(meetToCheck -> meetToCheck.equals(meet));

			if (isMeetUnique) {
				meetHashMap.put(meet.getUuid(), meet);
				saveMapChange();
			}
			else
				notUniqueMeet.add(meet);
		});

		if (!notUniqueMeet.isEmpty()) {
			var error = new StringBuffer();
			error.append("Alcuni meet esistono gia:\n");
			notUniqueMeet.forEach(meet -> error.append("\t• %s %s %s: %s-%s\n".formatted(meet.getCity(), meet.getSquare(), meet.getDay().toString(), meet.getStartTime().toString(), meet.getEndTime().toString())));
			System.out.println(error);
			throw new AlreadyExistException(error.toString());
		}
	}

	public void addNewMeet(String city, String square, ArrayList<String> days, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {
		for (String day : days) {
			addNewMeet(city, square, DateParser.stringToWeekDay(day), intervalStartTime, intervalEndTime, daysBeforeExpire);
		}
	}

	public void bookMeet(String meetUuid, String userUuid) {
		var meet =  Optional.ofNullable(meetHashMap.get(meetUuid));

		if (meet.isPresent()) {
			meet.get().bookMeet(userUuid);
			saveMapChange();
		}
	}

	public void unBookMeet(String meetUuid) {
		var meet =  Optional.ofNullable(meetHashMap.get(meetUuid));

		meet.orElseThrow(NullPointerException::new).unbookMeet();
		saveMapChange();
	}

	public List<Meet> getMeets() {
		return meetHashMap.values().stream().toList();
	}

	void saveMapChange() {
		try {
			objectMapper.writeValue(jsonFile, meetHashMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.meetUpdaterDaemon = new MeetUpdaterDaemon(meetHashMap);
	}

	private ArrayList<LocalTime> generateIntervals(int start, int end) throws IllegalValuesException {
		if (start <= end) {
			ArrayList<LocalTime> tmpList = new ArrayList<>();

			int time = start;
			while (time <= end) {
				tmpList.add(LocalTime.of(time / 60, time % 60));
				time += 30;
			}

			return tmpList;
		}
		else {
			throw new IllegalValuesException("INVALID_TIME_INPUT");
		}
	}
}
