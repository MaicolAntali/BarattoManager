package com.barattoManager.trade;

import com.barattoManager.article.Article;
import com.barattoManager.article.ArticleManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class TradeManager {

	private final File tradeFile = new File(AppConfigurator.getInstance().getFileName("trade_file"));
	private final ObjectMapper objectMapper = JsonMapper.builder()
			.addModule(new ParameterNamesModule())
			.addModule(new Jdk8Module())
			.addModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();
	private final HashMap<String, Trade> tradeHashMap;

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

	public void runDaemonChecker() {
		// Check if the thread is already running
		if (daemonChecker == null || !daemonChecker.isAlive()) {
			daemonChecker = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							new TimerTask() {
								@Override
								public void run() {
									System.out.printf("Running checker: %s%n", LocalDateTime.now());
									if (!tradeHashMap.isEmpty()) {
										AtomicBoolean hashmapHasChanged = new AtomicBoolean(false);
										ArrayList<Trade> tradeToRemove = new ArrayList<>();
										tradeHashMap.values().stream()
												.filter(trade -> LocalDateTime.now().isAfter(trade.tradeEndDateTime()))
												.forEach(trade -> {
													ArticleManager.getInstance().getArticleById(trade.articleOneUuid())
															.orElseThrow(NullPointerException::new)
															.changeState(Article.State.OPEN_OFFERT);
													ArticleManager.getInstance().getArticleById(trade.articleTwoUuid())
															.orElseThrow(NullPointerException::new)
															.changeState(Article.State.OPEN_OFFERT);

													tradeToRemove.add(trade);
													hashmapHasChanged.set(true);
												});
										if (hashmapHasChanged.get()) {
											tradeToRemove.forEach(trade -> tradeHashMap.remove(trade.uuid()));
											saveTradeMapChange();
										}
									}
								}
							},
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
	}
}
