package com.barattoManager.manager;

import com.barattoManager.manager.daemon.TradeCheckerDaemon;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.utils.AppConfigurator;
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
import java.util.Timer;

public final class TradeManager {

	private final File tradeFile = new File(AppConfigurator.getInstance().getFileName("trade_file"));
	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();
	private final HashMap<String, Trade> tradeHashMap;

	private TradeCheckerDaemon tradeCheckerDaemon;
	private Thread daemonChecker;

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

		this.tradeCheckerDaemon = new TradeCheckerDaemon(tradeHashMap);
	}

	public void addNewTrade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid) {

		ArticleManager.getInstance().getArticleById(articleOneUuid)
				.orElseThrow(NullPointerException::new).changeState(Article.State.LINKED_OFFERT);
		ArticleManager.getInstance().getArticleById(articleTwoUuid)
				.orElseThrow(NullPointerException::new).changeState(Article.State.SELECTED_OFFERT);

		var trade = new Trade(endTradeDateTime, articleOneUuid, articleTwoUuid);
		tradeHashMap.put(trade.uuid(), trade);
		saveTradeMapChange();
	}

	public static TradeManager getInstance() {
		return TradeManagerHolder.instance;
	}

	public void removeTradeByUuid(String uuid) {
		tradeHashMap.remove(uuid);
		saveTradeMapChange();
	}


	public void runDaemonChecker() {
		if (daemonChecker == null || !daemonChecker.isAlive()) {
			daemonChecker = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							tradeCheckerDaemon,
							0,
							60*1_000
					)
			);
			daemonChecker.setDaemon(true);
			daemonChecker.start();
		}
	}

	private static final class  TradeManagerHolder {
		/**
		 * Instance of {@link TradeManager}
		 */
		private static final TradeManager instance = new TradeManager();
	}

	private void saveTradeMapChange() {
		try {
			objectMapper.writeValue(tradeFile, tradeHashMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.tradeCheckerDaemon = new TradeCheckerDaemon(tradeHashMap);
	}
}
