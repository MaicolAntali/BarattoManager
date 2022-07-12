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

public class TradeCheckerDaemon extends TimerTask {

	private final ConcurrentHashMap<String, Trade> tradeHashMap;

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
