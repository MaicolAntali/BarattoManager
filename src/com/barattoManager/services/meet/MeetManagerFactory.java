package com.barattoManager.services.meet;

import com.barattoManager.services.category.CategoryManager;
import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Class that constructs the {@link MeetManager}<br/>
 * {@link MeetManager}is declared in the class as a static field, to ensure one instance for the whole project.
 * It implements {@link Runnable} because it is run in a separated thread
 */
public class MeetManagerFactory implements Runnable {

	private static MeetManager MEET_MANAGER;
	/**
	 * Method used to get the meet manager
	 * @return MEET_MANAGER {@link MeetManager}
	 */
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
