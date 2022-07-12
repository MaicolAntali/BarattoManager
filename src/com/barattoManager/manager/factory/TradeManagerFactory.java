package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class TradeManagerFactory {

	private static final TradeManager TRADE_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, Trade>(
				AppConfigurator.getInstance().getFileName("trades"),
				JsonMapper.builder()
						.addModule(new ParameterNamesModule())
						.addModule(new Jdk8Module())
						.addModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.build()
		);

		TRADE_MANAGER = new TradeManager(jsonHandler.readJson(String.class, Trade.class));

		EventFactory.getTradesEvent().addListener(jsonHandler);
	}

	public static TradeManager getManager() {
		return TRADE_MANAGER;
	}
}
