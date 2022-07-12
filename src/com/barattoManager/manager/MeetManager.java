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
import java.util.*;
import java.util.stream.IntStream;

import static com.barattoManager.manager.Constants.*;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the meets.
 */
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

	/**
	 * 	Holder class of instance
	 */
	private static final class MeetManagerHolder {

		/**
		 * Instance of {@link MeetManager}
		 */
		private static final MeetManager instance = new MeetManager();
	}

	/**
	 * Method used to create get the {@link MeetManager} instance.
	 * This method uses the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code #getInstance()} method is called.
	 *
	 * @return The Instance of {@link MeetManager} class
	 */
	public static MeetManager getInstance() {
		return MeetManagerHolder.instance;
	}

	/**
	 * Method used to run the updater thread
	 */
	public void runUpdaterDaemon() {
		if (daemonThread == null || !daemonThread.isAlive()) {
			daemonThread = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							meetUpdaterDaemon,
							15000, // 15 sec
							60000 // 1 Minutes
					)
			);
			daemonThread.setDaemon(true);
			daemonThread.start();
		}
	}

	/**
	 * Method used to add a new {@link Meet}
	 * @param city City of the meet
	 * @param square Square of the meet
	 * @param day Day of the meet
	 * @param intervalStartTime Start time of the meet
	 * @param intervalEndTime End time of the meet
	 * @param daysBeforeExpire Days before expire of the meet
	 * @throws AlreadyExistException Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
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
				assert getDataMap().containsKey(meet.getUuid()) : POST_CONDITION_THE_MEET_IS_NOT_PRESENT_IN_THE_MAP;

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

	/**
	 * Method used to add a new {@link Meet}
	 * @param city City of the meet
	 * @param square Square of the meet
	 * @param days Days of the meet
	 * @param intervalStartTime Start time of the meet
	 * @param intervalEndTime End time of the meet
	 * @param daysBeforeExpire Days before expire of the meet
	 * @throws AlreadyExistException Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
	public void addNewMeet(String city, String square, ArrayList<String> days, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {
		for (String day : days) {
			addNewMeet(city, square, DateParser.stringToWeekDay(day), intervalStartTime, intervalEndTime, daysBeforeExpire);
		}
	}

	/**
	 * Method used to book a {@link Meet}
	 * @param meetUuid {@link UUID} of the meet
	 * @param userUuid {@link UUID} of the user
	 */
	public void bookMeet(String meetUuid, String userUuid) {
		var meet =  Optional.ofNullable(getDataMap().get(meetUuid));

		if (meet.isPresent()) {
			meet.get().bookMeet(userUuid);
			saveDataMap();
		}
	}

	/**
	 * Method used to un-book a {@link Meet}
	 * @param meetUuid {@link UUID} of the meet
	 */
	public void unbookMeet(String meetUuid) {
		var meet =  Optional.ofNullable(getDataMap().get(meetUuid));

		if (meet.isPresent()) {
			meet.get().unbookMeet();
			saveDataMap();
		}
	}

	/**
	 * Method used to get the meets
	 * @return {@link List} of meets
	 */
	public List<Meet> getMeets() {
		return getDataMap().values().stream().toList();
	}

	/**
	 * Method used to get the available meets
	 * @return {@link List} of available meets
	 */
	public List<Meet> getAvailableMeet() {
		return getDataMap().values().stream()
				.filter(meet -> meet.getUserBookedMeetUuid().isEmpty())
				.toList();
	}

	/**
	 * Method used to get the meet by the UUID
	 * @param meetUuid {@link UUID} of the {@link Meet}
	 * @return {@link Optional} of available meets
	 */
	public Optional<Meet> getMeetByUuid(String meetUuid) {
		return Optional.ofNullable(getDataMap().get(meetUuid));
	}

	/**
	 * Method used to remove the meet by the UUID, from the map
	 * @param meetUuid {@link UUID} of the {@link Meet} to remove
	 */
	public void removeMeetByUuid(String meetUuid) {
		getDataMap().remove(meetUuid);
		saveDataMap();
	}

	/**
	 * Method used to generate the intervals of time for the meet
	 * @param start Start time of interval
	 * @param end end time of interval
	 * @return {@link ArrayList} of intervals
	 * @throws IllegalValuesException Is thrown if the time input is invalid
	 */
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
