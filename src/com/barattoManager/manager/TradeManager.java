package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.daemon.TradeCheckerDaemon;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.trade.Trade;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;

public final class TradeManager extends ConcurrencyManager<String, Trade> {
	private TradeCheckerDaemon tradeCheckerDaemon;
	private Thread daemonChecker;

	private TradeManager() {
		super(String.class, Trade.class);

		this.tradeCheckerDaemon = new TradeCheckerDaemon(getDataMap());
	}

	@Override
	File getJsonFile() {
		return new File(AppConfigurator.getInstance().getFileName("trade_file"));
	}

	@Override
	ObjectMapper getObjectMapper() {
		return JsonMapper.builder()
				.addModule(new ParameterNamesModule())
				.addModule(new Jdk8Module())
				.addModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
	}

	@Override
	void afterDataChangeActions() {
		this.tradeCheckerDaemon = new TradeCheckerDaemon(getDataMap());
		EventFactory.getTradesEvent().fireListener();
	}

	public void addNewTrade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {

		ArticleManager.getInstance().getArticleById(articleOneUuid)
				.orElseThrow(NullPointerException::new).changeState(Article.State.LINKED_OFFER);
		ArticleManager.getInstance().getArticleById(articleTwoUuid)
				.orElseThrow(NullPointerException::new).changeState(Article.State.SELECTED_OFFER);

		var trade = new Trade(endTradeDateTime, articleOneUuid, articleTwoUuid, meetUuid);
		getDataMap().put(trade.getUuid(), trade);
		saveDataMap();
	}


	public static TradeManager getInstance() {
		return TradeManagerHolder.instance;
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

	private static final class TradeManagerHolder {
		/**
		 * Instance of {@link TradeManager}
		 */
		private static final TradeManager instance = new TradeManager();
	}

	public List<Trade> getTradeByUser(String userUuid) {
		return getDataMap().values().stream()
				.filter(trade ->
						Objects.equals(ArticleManager.getInstance().getArticleById(trade.getArticleOneUuid()).orElseThrow(NullPointerException::new)
								.getUserNameOwner(), userUuid)
								|| Objects.equals(ArticleManager.getInstance().getArticleById(trade.getArticleTwoUuid()).orElseThrow(NullPointerException::new)
								.getUserNameOwner(), userUuid))
				.toList();
	}

	public Optional<Trade> getTradeByUuid(String tradeUuid) {
		return Optional.ofNullable(getDataMap().get(tradeUuid));
	}
}
