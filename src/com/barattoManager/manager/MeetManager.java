package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.daemon.MeetUpdaterDaemon;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.utils.AppConfigurator;
import com.barattoManager.utils.parser.DateParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.stream.IntStream;

public final class MeetManager extends ConcurrencyManager<String, Meet> {

	private  MeetUpdaterDaemon meetUpdaterDaemon;
	private Thread daemonThread;

	private MeetManager() {
		super(String.class, Meet.class);

		this.meetUpdaterDaemon = new MeetUpdaterDaemon(getDataMap());
	}

	@Override
	File getJsonFile() {
		return new File(AppConfigurator.getInstance().getFileName("meet_file"));
	}

	@Override
	ObjectMapper getObjectMapper() {
		return JsonMapper.builder()
				.addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module())
				.addModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
	}

	@Override
	void afterDataChangeActions() {
		this.meetUpdaterDaemon = new MeetUpdaterDaemon(getDataMap());
		EventFactory.getMeetsEvent().fireListener();
	}

	private static final class MeetManagerHolder {
		private static final MeetManager instance = new MeetManager();
	}

	public static MeetManager getInstance() {
		return MeetManagerHolder.instance;
	}

	public void runUpdaterDaemon() {
		if (daemonThread == null || !daemonThread.isAlive()) {
			daemonThread = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							meetUpdaterDaemon,
							30000, // 30 sec
							60000 // 1 Minutes
					)
			);
			daemonThread.setDaemon(true);
			daemonThread.start();
		}
	}

	public void addNewMeet(String city, String square, DayOfWeek day, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		var intervals = generateIntervals(intervalStartTime, intervalEndTime);

		var meets = new ArrayList<Meet>();
		IntStream.range(0, intervals.size()-1)
				.forEach(i -> meets.add(new Meet(city, square, day, intervals.get(i), intervals.get(i+1), daysBeforeExpire)));


		var notUniqueMeet = new ArrayList<Meet>();
		meets.forEach(meet -> {
			var isMeetUnique = getDataMap().values().stream()
					.noneMatch(meetToCheck -> meetToCheck.equals(meet));

			if (isMeetUnique) {
				getDataMap().put(meet.getUuid(), meet);
				saveDataMap();
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
		var meet =  Optional.ofNullable(getDataMap().get(meetUuid));

		if (meet.isPresent()) {
			meet.get().bookMeet(userUuid);
			saveDataMap();
		}
	}

	public List<Meet> getMeets() {
		return getDataMap().values().stream().toList();
	}

	public List<Meet> getAvailableMeet() {
		return getDataMap().values().stream()
				.filter(meet -> meet.getUserBookedMeetUuid().isEmpty())
				.toList();
	}

	public Optional<Meet> getMeetByUuid(String meetUuid) {
		return Optional.ofNullable(getDataMap().get(meetUuid));
	}

	public void removeMeetByUuid(String meetUuid) {
		getDataMap().remove(meetUuid);
		saveDataMap();
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
