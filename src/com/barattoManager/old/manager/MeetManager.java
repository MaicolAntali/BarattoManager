package com.barattoManager.old.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.manager.daemon.MeetUpdaterDaemon;
import com.barattoManager.old.sample.meet.Meet;
import com.barattoManager.old.utils.parser.DateParser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Class that handles meets
 */
public final class MeetManager implements Manager {

	private static final String ERROR_INSERT_ALL_FIELDS = "Uno dei valore richiesti è stato lasciato vuoto. Inserire tutti i campi.";
	private static final String ERROR_NO_DAY_SELECTED = "Non è stato selezionato nessun giorno.";

	private final ConcurrentHashMap<String, Meet> meetMap;
	private MeetUpdaterDaemon meetUpdaterDaemon;
	private Thread daemonThread;

	/**
	 * Constructor of the class
	 *
	 * @param meetMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the meets.
	 */
	public MeetManager(ConcurrentHashMap<String, Meet> meetMap) {
		this.meetMap = meetMap;
		this.meetUpdaterDaemon = new MeetUpdaterDaemon(meetMap);
	}

	/**
	 * method used to start the thread where every minute will run {@link MeetUpdaterDaemon}
	 */
	public void runUpdaterDaemon() {
		if (daemonThread == null || !daemonThread.isAlive()) {
			daemonThread = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							meetUpdaterDaemon,
							5000, // 5 sec
							60000 // 1 Minutes
					)
			);
			daemonThread.setDaemon(true);
			daemonThread.start();
		}
	}

	/**
	 * Method used to add a new meet
	 *
	 * @param city              City of the new meet
	 * @param square            Square of the new meet
	 * @param day               {@link DayOfWeek} of the new meet
	 * @param intervalStartTime Start time of the meeting in minutes
	 * @param intervalEndTime   End time of the meeting in minutes
	 * @param daysBeforeExpire  Number of days for which the meet is valid
	 * @throws AlreadyExistException  Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
	public void addNewMeet(String city, String square, DayOfWeek day, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		if (city.isBlank() || square.isBlank() || daysBeforeExpire <= 0)
			throw new IllegalValuesException(ERROR_INSERT_ALL_FIELDS);

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
	 * Method used to add a new meet
	 *
	 * @param city              City of the new meet
	 * @param square            Square of the new meet
	 * @param days              {@link ArrayList} of {@link DayOfWeek}
	 * @param intervalStartTime Start time of the meeting in minutes
	 * @param intervalEndTime   End time of the meeting in minutes
	 * @param daysBeforeExpire  Number of days for which the meet is valid
	 * @throws AlreadyExistException  Is thrown if the meet already exists
	 * @throws IllegalValuesException Is thrown if the meet contains illegal values
	 */
	public void addNewMeet(String city, String square, ArrayList<String> days, int intervalStartTime, int intervalEndTime, int daysBeforeExpire) throws AlreadyExistException, IllegalValuesException {

		if (days.isEmpty())
			throw new IllegalValuesException(ERROR_NO_DAY_SELECTED);

		for (String day : days) {
			addNewMeet(city, square, DateParser.stringToWeekDay(day), intervalStartTime, intervalEndTime, daysBeforeExpire);
		}
	}

	/**
	 * Method used to book a meet
	 *
	 * @param meetUuid Uuid of the meeting to be booked
	 * @param userUuid Username of the user booking the meet
	 */
	public void bookMeet(String meetUuid, String userUuid) {
		var meet = Optional.ofNullable(this.meetMap.get(meetUuid));

		if (meet.isPresent()) {
			meet.get().bookMeet(userUuid);
			saveData();
		}
	}

	/**
	 * Method used to un-book a meet
	 *
	 * @param meetUuid Uuid of the meeting to be un-booked
	 */
	public void unBookMeet(String meetUuid) {
		var meet = Optional.ofNullable(this.meetMap.get(meetUuid));

		if (meet.isPresent()) {
			meet.get().unBookMeet();
			saveData();
		}
	}

	/**
	 * Method used to get the {@link List} with all {@link Meet meets}
	 *
	 * @return {@link List} with all {@link Meet meets}
	 */
	public List<Meet> getMeets() {
		return this.meetMap.values().stream().toList();
	}

	/**
	 * Method used to get the {@link List} with all {@link Meet} available (meet not booked)
	 *
	 * @return {@link List} with all {@link Meet} available (meet not booked)
	 */
	public List<Meet> getAvailableMeet() {
		return this.meetMap.values().stream()
				.filter(meet -> meet.getUserBookedMeetUuid().isEmpty())
				.toList();
	}

	/**
	 * Method used to return a meet by its uuid<br/>
	 * The method returns an {@link Optional}  with the {@link Meet} object if the past uuid is found otherwise an empty {@link Optional}
	 *
	 * @param meetUuid uuid of the {@link Meet} to get
	 * @return An {@link Optional} with the object {@link Meet} otherwise an empty {@link Optional}
	 */
	public Optional<Meet> getMeetByUuid(String meetUuid) {
		return Optional.ofNullable(this.meetMap.get(meetUuid));
	}

	/**
	 * method used to remove/delete a {@link Meet}
	 *
	 * @param meetUuid uuid of the {@link Meet} to be removed
	 */
	public void removeMeetByUuid(String meetUuid) {
		this.meetMap.remove(meetUuid);
		saveData();
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

	private void saveData() {
		this.meetUpdaterDaemon = new MeetUpdaterDaemon(this.meetMap);
		EventFactory.getMeetsEvent().fireListener(this.meetMap);
	}
}
