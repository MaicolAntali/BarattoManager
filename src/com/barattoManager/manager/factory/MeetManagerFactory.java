package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.MeetManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.meet.Meet;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class MeetManagerFactory {

	private static final MeetManager MEET_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, Meet>(
				AppConfigurator.getInstance().getFileName("meets"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		MEET_MANAGER = new MeetManager(jsonHandler.readJson(String.class, Meet.class));

		EventFactory.getMeetsEvent().addListener(jsonHandler);
	}

	public static MeetManager getManager() {
		return MEET_MANAGER;
	}
}
