package com.barattoManager.manager.daemon;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.utils.parser.TimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MeetUpdaterDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Meet> meetHashMap;

	public MeetUpdaterDaemon(ConcurrentHashMap<String, Meet> meetHashMap) {
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

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManager.getInstance().removeMeetByUuid(meet.getUuid());
					}
				});

		meetHashMap.values().stream()
				.filter(meet -> !meet.isAlreadyUpdated())
				.filter(meet -> LocalDate.now().isEqual(meet.getDateOfMeet()))
				.filter(meet -> LocalTime.now().isAfter(meet.getEndTime()))
				.forEach(meet -> {
					createNewMeet(meet);

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManager.getInstance().removeMeetByUuid(meet.getUuid());
					}
				});

		System.out.printf("Ended MeetUpdaterDaemon: %s%n", LocalDateTime.now());
	}

	private void createNewMeet(Meet meet) {
		meet.setAlreadyUpdated(true);
		try {
			MeetManager.getInstance().addNewMeet(
					meet.getCity(),
					meet.getSquare(),
					meet.getDay(),
					TimeParser.hourToMinuteTime(meet.getStartTime().getHour(), meet.getStartTime().getMinute()),
					TimeParser.hourToMinuteTime(meet.getEndTime().getHour(), meet.getEndTime().getMinute()),
					meet.getDaysBeforeExpire()
			);
		} catch (AlreadyExistException | IllegalValuesException e) {
			throw new RuntimeException(e);
		}
	}

}
