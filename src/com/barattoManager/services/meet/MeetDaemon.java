package com.barattoManager.services.meet;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.utils.parser.TimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MeetDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Meet> meetHashMap;

	/**
	 * Constructor of the class
	 *
	 * @param meetHashMap {@link ConcurrentHashMap} that contains all the meet to check
	 */
	public MeetDaemon(ConcurrentHashMap<String, Meet> meetHashMap) {
		this.meetHashMap = meetHashMap;
	}

	@Override
	public void run() {
		System.out.printf("Running  MeetUpdaterDaemon: %s%n", LocalDateTime.now());

		meetHashMap.values().stream()
				.filter(meet -> !meet.isAlreadyUpdated())
				.filter(meet -> LocalDate.now().isAfter(meet.getDateOfMeet()))
				.forEach(meet -> {
					createNewMeet(meet);

					meet.setAlreadyUpdated(true);

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManagerFactory.getManager().removeMeetByUuid(meet.getUuid());
					}
				});

		meetHashMap.values().stream()
				.filter(meet -> !meet.isAlreadyUpdated())
				.filter(meet -> LocalDate.now().isEqual(meet.getDateOfMeet()))
				.filter(meet -> LocalTime.now().isAfter(meet.getEndTime()))
				.forEach(meet -> {
					createNewMeet(meet);

					meet.setAlreadyUpdated(true);

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManagerFactory.getManager().removeMeetByUuid(meet.getUuid());
					}
				});

		System.out.printf("Ended MeetUpdaterDaemon: %s%n", LocalDateTime.now());
	}

	private void createNewMeet(Meet meet) {
		meet.setAlreadyUpdated(true);
		try {
			MeetManagerFactory.getManager().addNewMeet(
					meet.getCity(),
					meet.getSquare(),
					meet.getDay(),
					TimeParser.hourToMinuteTime(meet.getStartTime().getHour(), meet.getStartTime().getMinute()),
					TimeParser.hourToMinuteTime(meet.getEndTime().getHour(), meet.getEndTime().getMinute()),
					meet.getDaysBeforeExpire()
			);
		} catch (AlreadyExistException | InvalidArgumentException e) {
			throw new RuntimeException(e);
		}
	}
}
