package com.barattoManager.meet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TimerTask;

public class MeetUpdaterDaemon extends TimerTask {

	private final HashMap<String, Meet> meetHashMap;

	public MeetUpdaterDaemon(HashMap<String, Meet> meetHashMap) {
		this.meetHashMap = meetHashMap;
	}

	@Override
	public void run() {
		System.out.printf("Running the MeetUpdaterDaemon: %s%n", LocalDateTime.now());
		meetHashMap.values().forEach(meet -> {

			if (LocalDate.now().isAfter(meet.getDateOfMeet())) {
				do {
					meet.setDateOfMeet(meet.getDateOfMeet().plusDays(7));
				} while (meet.getDateOfMeet().isBefore(LocalDate.now()) || meet.getDateOfMeet().isEqual(LocalDate.now()));

				MeetManager.getInstance().unBookMeet(meet.getUuid());
				System.out.printf("\tCleared the meet: %s%n", meet.getUuid());
			}

			if (LocalDate.now().isEqual(meet.getDateOfMeet())) {
				if (LocalTime.now().isAfter(meet.getEndTime())) {
					meet.setDateOfMeet(meet.getDateOfMeet().plusDays(7));
					MeetManager.getInstance().unBookMeet(meet.getUuid());
					System.out.printf("\tCleared the meet: %s%n", meet.getUuid());
				}
			}
		});
		System.out.printf("Ended the MeetUpdaterDaemon: %s%n", LocalDateTime.now());
	}
}
