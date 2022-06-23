package com.barattoManager.manager.daemon;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class TradeCheckerDaemon extends TimerTask {

	private final HashMap<String, Trade> tradeHashMap;

	public TradeCheckerDaemon(HashMap<String, Trade> tradeHashMap) {
		this.tradeHashMap = tradeHashMap;
	}

	@Override
	public void run() {
		System.out.printf("Running TradeCheckerDaemon: %s%n", LocalDateTime.now());
		if (!tradeHashMap.isEmpty()) {

			ArrayList<Trade> meetToRemove = new ArrayList<>();

			tradeHashMap.values().stream()
					.filter(trade -> LocalDateTime.now().isAfter(trade.tradeEndDateTime()))
					.forEach(trade -> {
						ArticleManager.getInstance().getArticleById(trade.articleOneUuid())
								.orElseThrow(NullPointerException::new)
								.changeState(Article.State.OPEN_OFFER);
						ArticleManager.getInstance().getArticleById(trade.articleTwoUuid())
								.orElseThrow(NullPointerException::new)
								.changeState(Article.State.OPEN_OFFER);

						meetToRemove.add(trade);
					});

			if (!meetToRemove.isEmpty())
				meetToRemove.forEach(trade -> TradeManager.getInstance().removeTradeByUuid(trade.uuid()));
		}
		System.out.printf("Ended TradeCheckerDaemon: %s%n", LocalDateTime.now());
	}
}
