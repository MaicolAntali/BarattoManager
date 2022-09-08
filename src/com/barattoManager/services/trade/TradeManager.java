package com.barattoManager.services.trade;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles and contains the data structure of the trades
 */
public class TradeManager {
	private final ConcurrentHashMap<String, Trade> tradeMap;
	private TradeDaemon tradeDaemon;
	private Thread daemonChecker;

	/**
	 * Constructor of the class
	 *
	 * @param tradeMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the trades.
	 */
	public TradeManager(ConcurrentHashMap<String, Trade> tradeMap) {
		this.tradeMap = tradeMap;
		this.tradeDaemon = new TradeDaemon(tradeMap);

		runDaemon();
	}


	/**
	 * Method used to add a new trade
	 *
	 * @param endTradeDateTime {@link LocalDateTime} Expiry date of the trade
	 * @param articleOneUuid   Uid of the article one involved in the trade
	 * @param articleTwoUuid   Uuid of the article two involved in the trade
	 * @param meetUuid         Uuid of the meet
	 */
	public void addNewTrade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {

		try {
			ArticleManagerFactory.getManager()
					.changeArticleState(
							articleOneUuid,
							Article.State.LINKED_OFFER
					);
		} catch (InvalidArgumentException e) {
			throw new RuntimeException(e);
		}

		try {
			ArticleManagerFactory.getManager()
					.changeArticleState(
							articleTwoUuid,
							Article.State.SELECTED_OFFER
					);
		} catch (InvalidArgumentException e) {
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
	 * @param tradeUuid uuid of the {@link Trade} to get
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

	/**
	 * Method used to save the data of the trades
	 */
	public void saveData() {
		this.tradeDaemon = new TradeDaemon(this.tradeMap);
		TradeUpdateDataEventFactory.getEventHandler().fireUpdateListeners(this.tradeMap);
	}

	private void runDaemon() {
		if (daemonChecker == null || !daemonChecker.isAlive()) {
			daemonChecker = new Thread(
					() -> new Timer().scheduleAtFixedRate(
							tradeDaemon,
							0,
							60 * 1_000
					)
			);
			daemonChecker.setDaemon(true);
			daemonChecker.start();
		}
	}
}
