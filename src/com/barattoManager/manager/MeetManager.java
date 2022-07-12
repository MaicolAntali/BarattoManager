package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.daemon.MeetUpdaterDaemon;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.utils.parser.DateParser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public final class MeetManager implements Manager {

	private final ConcurrentHashMap<String, Meet> meetMap;
	private MeetUpdaterDaemon meetUpdaterDaemon;
	private Thread daemonThread;

	public MeetManager(ConcurrentHashMap<String, Meet> meetMap) {
		this.meetMap = meetMap;
		this.meetUpdaterDaemon = new MeetUpdaterDaemon(meetMap);
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
	 *
	 * @param city              City of the meet
	 * @param square            Square of the meet
	 * @param day               Day of the meet
	 * @param intervalStartTime Start time of the meet
	 * @param intervalEndTime   End time of the meet
	 * @param daysBeforeExpire  Days before expire of the meet
	 * @throws AlreadyExistException  Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
	public void addNewMeet(String city, String square, DayOfWeek day, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		if (city.isBlank() || square.isBlank() || daysBeforeExpire <= 0)
			throw new IllegalValuesException("Uno dei valore richiesti è stato lasciato vuoto. Inserire tutti i campi.");

		var intervals = generateIntervals(intervalStartTime, intervalEndTime);

		var meets = new ArrayList<Meet>();
		IntStream.range(0, intervals.size() - 1)
				.forEach(i -> meets.add(new Meet(city, square, day, intervals.get(i), intervals.get(i + 1), daysBeforeExpire)));


		var notUniqueMeet = new ArrayList<Meet>();
		meets.forEach(meet -> {
			var isMeetUnique = this.meetMap.values().stream()
					.noneMatch(meetToCheck -> meetToCheck.equals(meet));

			if (isMeetUnique) {
				this.meetMap.put(meet.getUuid(), meet);
				saveData();
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
	 *
	 * @param city              City of the meet
	 * @param square            Square of the meet
	 * @param days              Days of the meet
	 * @param intervalStartTime Start time of the meet
	 * @param intervalEndTime   End time of the meet
	 * @param daysBeforeExpire  Days before expire of the meet
	 * @throws AlreadyExistException  Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
	public void addNewMeet(String city, String square, ArrayList<String> days, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		if (days.isEmpty())
			throw new IllegalValuesException("Non è stato selezionato nessun giorno.");

		for (String day : days) {
			addNewMeet(city, square, DateParser.stringToWeekDay(day), intervalStartTime, intervalEndTime, daysBeforeExpire);
		}
	}

	/**
	 * Method used to book a {@link Meet}
	 *
	 * @param meetUuid uuid of the meet
	 * @param userUuid uuid  of the user
	 */
	public void bookMeet(String meetUuid, String userUuid) {
		var meet = Optional.ofNullable(this.meetMap.get(meetUuid));

		if (meet.isPresent()) {
			meet.get().bookMeet(userUuid);
			saveData();
		}
	}

	public void unBookMeet(String meetUuid) {
		var meet = Optional.ofNullable(this.meetMap.get(meetUuid));

		if (meet.isPresent()) {
			meet.get().unbookMeet();
			saveData();
		}
	}

	/**
	 * Method used to get the meets
	 *
	 * @return {@link List} of meets
	 */
	public List<Meet> getMeets() {
		return this.meetMap.values().stream().toList();
	}

	/**
	 * Method used to get the available meets
	 *
	 * @return {@link List} of available meets
	 */
	public List<Meet> getAvailableMeet() {
		return this.meetMap.values().stream()
				.filter(meet -> meet.getUserBookedMeetUuid().isEmpty())
				.toList();
	}

	/**
	 * Method used to get the meet by the UUID
	 *
	 * @param meetUuid uuid of the {@link Meet}
	 * @return {@link Optional} of available meets
	 */
	public Optional<Meet> getMeetByUuid(String meetUuid) {
		return Optional.ofNullable(this.meetMap.get(meetUuid));
	}

	/**
	 * Method used to remove the meet by the UUID, from the map
	 *
	 * @param meetUuid uuid of the {@link Meet} to remove
	 */
	public void removeMeetByUuid(String meetUuid) {
		this.meetMap.remove(meetUuid);
		saveData();
	}

	/**
	 * Method used to generate the intervals of time for the meet
	 *
	 * @param start Start time of interval
	 * @param end   end time of interval
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

	private void saveData() {
		this.meetUpdaterDaemon = new MeetUpdaterDaemon(this.meetMap);
		EventFactory.getMeetsEvent().fireListener(this.meetMap);
	}
}
