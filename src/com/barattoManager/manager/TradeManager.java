package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.daemon.TradeCheckerDaemon;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public final class TradeManager implements Manager {

	private final ConcurrentHashMap<String, Trade> tradeMap;
	private TradeCheckerDaemon tradeCheckerDaemon;
	private Thread daemonChecker;

	public TradeManager(ConcurrentHashMap<String, Trade> tradeMap) {
		this.tradeMap = tradeMap;
		this.tradeCheckerDaemon = new TradeCheckerDaemon(tradeMap);
	}

	public void addNewTrade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {

		try {
			ArticleManagerFactory.getManager()
					.changeArticleState(
							articleOneUuid,
							Article.State.LINKED_OFFER
					);
		} catch (IllegalValuesException e) {
			throw new RuntimeException(e);
		}

		try {
			ArticleManagerFactory.getManager()
					.changeArticleState(
							articleTwoUuid,
							Article.State.SELECTED_OFFER
					);
		} catch (IllegalValuesException e) {
			throw new RuntimeException(e);
		}


		var trade = new Trade(endTradeDateTime, articleOneUuid, articleTwoUuid, meetUuid);
		this.tradeMap.put(trade.getUuid(), trade);
		saveData();
	}

	public void runDaemonChecker() {
		if (daemonChecker == null || !daemonChecker.isAlive()) {
			daemonChecker = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							tradeCheckerDaemon,
							0,
							60 * 1_000
					)
			);
			daemonChecker.setDaemon(true);
			daemonChecker.start();
		}
	}

	public Optional<Trade> getTradeByUuid(String tradeUuid) {
		return Optional.ofNullable(this.tradeMap.get(tradeUuid));
	}

	public ConcurrentHashMap<String, Trade> getTradeMap() {
		return tradeMap;
	}

	public void saveData() {
		this.tradeCheckerDaemon = new TradeCheckerDaemon(this.tradeMap);
		EventFactory.getTradesEvent().fireListener(this.tradeMap);
	}
}
