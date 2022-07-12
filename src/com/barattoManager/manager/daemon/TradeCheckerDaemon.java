package com.barattoManager.manager.daemon;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.model.trade.TradeStatus;

import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Trade checker
 */
public class TradeCheckerDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Trade> tradeHashMap;

	/**
	 * {@link TradeCheckerDaemon} constructor
	 * @param tradeHashMap {@link ConcurrentHashMap} of trade
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
					ArticleManager.getInstance().getArticleById(trade.getArticleOneUuid())
							.orElseThrow(NullPointerException::new)
							.changeState(Article.State.OPEN_OFFER);
					ArticleManager.getInstance().getArticleById(trade.getArticleTwoUuid())
							.orElseThrow(NullPointerException::new)
							.changeState(Article.State.OPEN_OFFER);

					TradeManager.getInstance().getTradeByUuid(trade.getUuid())
							.orElseThrow(NullPointerException::new)
							.setTradeStatus(TradeStatus.CANCELLED);
				});

		System.out.printf("Ended TradeCheckerDaemon: %s%n", LocalDateTime.now());
	}
}
