package com.barattoManager.services.trade;

import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.article.Article;
import com.barattoManager.services.article.ArticleManagerFactory;

import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link TimerTask} used to check if a trade is expired and update its status.
 */
public class TradeDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Trade> tradeHashMap;

	/**
	 * Constructor of the class
	 *
	 * @param tradeHashMap {@link ConcurrentHashMap} that contains all the trade to check
	 */
	public TradeDaemon(ConcurrentHashMap<String, Trade> tradeHashMap) {
		this.tradeHashMap = tradeHashMap;
	}

	@Override
	public void run() {
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
					} catch (InvalidArgumentException e) {
						throw new RuntimeException(e);
					}

					try {
						ArticleManagerFactory.getManager()
								.changeArticleState(
										trade.getArticleTwoUuid(),
										Article.State.OPEN_OFFER
								);
					} catch (InvalidArgumentException e) {
						throw new RuntimeException(e);
					}

					TradeManagerFactory.getManager().getTradeByUuid(trade.getUuid())
							.orElseThrow(NullPointerException::new)
							.setTradeStatus(TradeStatus.CANCELLED);
				});
	}

}
