package com.barattoManager.manager.daemon;

import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.manager.factory.TradeManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.trade.TradeStatus;

import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class extending {@link TimerTask} so that it can be run in a separate thread.<br/>
 * The main purpose of the class is to check the {@link Trade} objects and verify that they are still valid.<br/>
 * A Trade is valid until the {@link Trade#getTradeEndDateTime() tradeEndDateTime} is in the past.
 */
public class TradeCheckerDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Trade> tradeHashMap;

	/**
	 * Constructor of the class
	 *
	 * @param tradeHashMap {@link ConcurrentHashMap} that contains all the trade to check
	 */
	public TradeCheckerDaemon(ConcurrentHashMap<String, Trade> tradeHashMap) {
		this.tradeHashMap = tradeHashMap;
	}

	@Override
	public void run() {
		System.out.printf("Running TradeCheckerDaemon: %s%n", LocalDateTime.now());

		tradeHashMap.values().stream()
				.filter(trade -> trade.getTradeStatus() == TradeStatus.IN_PROGRESS)
				.filter(trade -> LocalDateTime.now().isAfter(trade.getTradeEndDateTime()))
				.forEach(trade -> {
					try {
						ArticleManagerFactory.getManager()
								.changeArticleState(
										trade.getArticleOneUuid(),
										Article.State.OPEN_OFFER
								);
					} catch (IllegalValuesException e) {
						throw new RuntimeException(e);
					}

					try {
						ArticleManagerFactory.getManager()
								.changeArticleState(
										trade.getArticleTwoUuid(),
										Article.State.OPEN_OFFER
								);
					} catch (IllegalValuesException e) {
						throw new RuntimeException(e);
					}

					TradeManagerFactory.getManager().getTradeByUuid(trade.getUuid())
							.orElseThrow(NullPointerException::new)
							.setTradeStatus(TradeStatus.CANCELLED);
				});

		System.out.printf("Ended TradeCheckerDaemon: %s%n", LocalDateTime.now());
	}
}
