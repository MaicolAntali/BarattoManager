package com.barattoManager.services.meet;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class MeetManagerFactory implements Runnable {

	private static MeetManager MEET_MANAGER;

	public static MeetManager getManager() {
		return MEET_MANAGER;
	}

	@Override
	public void run() {
		var jsonHandler = new JsonHandler<String, Meet>(
				AppConfigurator.getInstance().getFileName("meets"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		MeetUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		MEET_MANAGER = new MeetManager(jsonHandler.readJson(String.class, Meet.class));
	}
}
