package com.barattoManager.services.trade;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class TradeManagerFactory implements Runnable {

	private static TradeManager TRADE_MANAGER;

	public static TradeManager getManager() {
		return TRADE_MANAGER;
	}

	@Override
	public void run() {
		var jsonHandler = new JsonHandler<String, Trade>(
				AppConfigurator.getInstance().getFileName("trades"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		TradeUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		TRADE_MANAGER = new TradeManager(jsonHandler.readJson(String.class, Trade.class));
	}
}
