package com.barattoManager.trade;

import com.barattoManager.config.AppConfigurator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class TradeManager {

	private final File tradeFile = new File(AppConfigurator.getInstance().getFileName("trade_file"));
	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();
	private final HashMap<String, Trade> tradeHashMap;
	private TradeManager() {
		if (tradeFile.exists()) {
			try {
				tradeHashMap = objectMapper.readValue(tradeFile, new TypeReference<>() {
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			tradeHashMap = new HashMap<>();
		}


		// Start daemon thread
		var daemonThread = new Thread(
				() -> {

				}
		);
		daemonThread.setDaemon(true);
		daemonThread.start();
	}

	public void addNewTrade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid) {
		var trade = new Trade(endTradeDateTime, articleOneUuid, articleTwoUuid);
		tradeHashMap.put(trade.uuid(), trade);
		saveTradeMapChange();
	}

	private static final class  TradeManagerHolder {
		/**
		 * Instance of {@link TradeManager}
		 */
		private static final TradeManager instance = new TradeManager();
	}

	public static TradeManager getInstance() {
		return TradeManagerHolder.instance;
	}

	private void saveTradeMapChange() {
		try {
			objectMapper.writeValue(tradeFile, tradeHashMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
