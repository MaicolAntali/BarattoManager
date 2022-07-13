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

/**
 * Class that handles trades
 */
public final class TradeManager implements Manager {

	private final ConcurrentHashMap<String, Trade> tradeMap;
	private TradeCheckerDaemon tradeCheckerDaemon;
	private Thread daemonChecker;

	/**
	 * Constructor of the class
	 *
	 * @param tradeMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the trades.
	 */
	public TradeManager(ConcurrentHashMap<String, Trade> tradeMap) {
		this.tradeMap = tradeMap;
		this.tradeCheckerDaemon = new TradeCheckerDaemon(tradeMap);
	}

	/**
	 * method used to start the thread where every minute will run {@link TradeCheckerDaemon}
	 */
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


	/**
	 * Method used to add a new trade
	 *
	 * @param endTradeDateTime {@link LocalDateTime} Expiry date of the trade
	 * @param articleOneUuid Uid of the article one involved in the trade
	 * @param articleTwoUuid Uuid of the article two involved in the trade
	 * @param meetUuid Uuid of the meet
	 */
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

	/**
	 * Method used to return a trade by its uuid<br/>
	 * The method returns an {@link Optional}  with the {@link Trade} object if the past uuid is found otherwise an empty {@link Optional}
	 *
	 * @return An {@link Optional} with the object {@link Trade} otherwise an empty {@link Optional}
	 */
	public Optional<Trade> getTradeByUuid(String tradeUuid) {
		return Optional.ofNullable(this.tradeMap.get(tradeUuid));
	}

	/**
	 * Method used to get the {@link ConcurrentHashMap} with all {@link Trade trades}
	 *
	 * @return {@link ConcurrentHashMap} with all {@link Trade trades}
	 */
	public ConcurrentHashMap<String, Trade> getTradeMap() {
		return tradeMap;
	}

	public void saveData() {
		this.tradeCheckerDaemon = new TradeCheckerDaemon(this.tradeMap);
		EventFactory.getTradesEvent().fireListener(this.tradeMap);
	}
}
