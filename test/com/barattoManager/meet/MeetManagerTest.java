package com.barattoManager.meet;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.manager.MeetManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MeetManagerTest {

	private MeetManager instance;

	@BeforeAll
	static void makeBackup() {
		var file = new File("meets.json");
		if (file.exists()) {
			file.renameTo(new File("meets_BACKUP.json"));
		}

		try {
			FileWriter myWriter = new FileWriter("meets.json");
			myWriter.write("""
						{
					    "d0e0ac79-d005-45cd-81e0-bfe0672bc03f": {
					      "uuid": "d0e0ac79-d005-45cd-81e0-bfe0672bc03f",
					      "city": "Brescia",
					      "square": "Stazione",
					      "day": "MONDAY",
					      "start_time": "10:00:00",
					      "end_time": "10:30:00",
					      "user_booked_uuid": null,
					      "day_before_expire": 3,
					      "date_of_meet": "2022-06-20"
					    },
					    "b3c99c58-653e-44c7-8a26-f3f2fa95a195": {
					      "uuid": "b3c99c58-653e-44c7-8a26-f3f2fa95a195",
					      "city": "Brescia",
					      "square": "Stazione",
					      "day": "MONDAY",
					      "start_time": "10:30:00",
					      "end_time": "11:00:00",
					      "user_booked_uuid": null,
					      "day_before_expire": 3,
					      "date_of_meet": "2022-06-20"
					    },
					    "6ac95e66-4de4-4dad-ba90-5430c87b697f": {
					      "uuid": "6ac95e66-4de4-4dad-ba90-5430c87b697f",
					      "city": "Brescia",
					      "square": "Stazione",
					      "day": "MONDAY",
					      "start_time": "09:30:00",
					      "end_time": "10:00:00",
					      "user_booked_uuid": null,
					      "day_before_expire": 3,
					      "date_of_meet": "2022-06-20"
					    },
					    "72c0ab38-b704-45f9-826d-d46534e0b7fa": {
					      "uuid": "72c0ab38-b704-45f9-826d-d46534e0b7fa",
					      "city": "Brescia",
					      "square": "Stazione",
					      "day": "MONDAY",
					      "start_time": "11:00:00",
					      "end_time": "11:30:00",
					      "user_booked_uuid": null,
					      "day_before_expire": 3,
					      "date_of_meet": "2022-06-20"
					    }
					  }
					""");
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void setUp() {
		instance = MeetManager.getInstance();
	}

	@AfterAll
	static void restoreBackup() {
		var file = new File("meets.json");
		file.delete();

		var backupFile = new File("meets_BACKUP.json");
		backupFile.renameTo(file);
	}

	@Test
	void addOverlappingMeetEasy() {
		assertThrows(AlreadyExistException.class, () -> instance
				.addNewMeet(
						"Brescia",
						"Stazione",
						DayOfWeek.MONDAY,
						9 * 60 + 30,
						11 * 60 + 30,
						3
				));
	}

	@Test
	void addOverlappingMeetHard() {
		assertThrows(AlreadyExistException.class, () -> instance
				.addNewMeet(
						"Brescia",
						"Stazione",
						DayOfWeek.MONDAY,
						8 * 60 + 10,
						18 * 60 + 40,
						3
				));
	}
}