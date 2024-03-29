package com.barattoManager.services.meet;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.utils.parser.TimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link TimerTask} used to check if a meet is in the past and update it
 */
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
		meetHashMap.values().stream()
				.filter(Meet::isMeetNotInPast)
				.filter(meet -> LocalDate.now().isAfter(meet.getDateOfMeet()))
				.forEach(meet -> {
					createNewMeet(meet);

					meet.setMeetNotInPast(false);

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManagerFactory.getManager().removeMeetByUuid(meet.getUuid());
					}
				});

		meetHashMap.values().stream()
				.filter(Meet::isMeetNotInPast)
				.filter(meet -> LocalDate.now().isEqual(meet.getDateOfMeet()))
				.filter(meet -> LocalTime.now().isAfter(meet.getEndTime()))
				.forEach(meet -> {
					createNewMeet(meet);

					meet.setMeetNotInPast(false);

					if (meet.getUserBookedMeetUuid().isEmpty()) {
						MeetManagerFactory.getManager().removeMeetByUuid(meet.getUuid());
					}
				});
	}

	private void createNewMeet(Meet meet) {
		meet.setMeetNotInPast(true);
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
