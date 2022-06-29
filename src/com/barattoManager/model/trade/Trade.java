package com.barattoManager.model.trade;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.utils.History;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Trade {
	@JsonProperty("uuid")
	private final String uuid;
	@JsonProperty("trade_start_date_time")
	private final LocalDateTime tradeStartDateTime;
	@JsonProperty("trade_end_date_time")
	private final LocalDateTime tradeEndDateTime;
	@JsonProperty("article_one_uuid")
	private final String articleOneUuid;
	@JsonProperty("article_two_uuid")
	private final String articleTwoUuid;
	@JsonProperty("answer")
	private final Answer answer;
	@JsonProperty("meet_uuid")
	private final String meetUuid;
	@JsonProperty("log")
	private final ArrayList<History> history;
	@JsonProperty("trade_status")
	private TradeStatus tradeStatus;


	public Trade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {
		this.uuid = UUID.randomUUID().toString();
		this.tradeStartDateTime =  LocalDateTime.now();
		this.tradeEndDateTime = endTradeDateTime;
		this.articleOneUuid = articleOneUuid;
		this.articleTwoUuid = articleTwoUuid;
		this.answer = new Answer(
				ArticleManager.getInstance().getArticleById(articleOneUuid).orElseThrow(NullPointerException::new).getUserNameOwner(),
				ArticleManager.getInstance().getArticleById(articleTwoUuid).orElseThrow(NullPointerException::new).getUserNameOwner()
		);
		this.meetUuid = meetUuid;
		this.history = new ArrayList<>();
		this.tradeStatus = TradeStatus.IN_PROGRESS;

		history.add(new History("Scambio creato", "Creazione dello scambio avvenuta con successo"));
	}

	public Trade(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("trade_start_date_time") LocalDateTime tradeStartDateTime,
			@JsonProperty("trade_end_date_time") LocalDateTime tradeEndDateTime,
			@JsonProperty("article_one_uuid") String articleOneUuid,
			@JsonProperty("article_two_uuid") String articleTwoUuid,
			@JsonProperty("answer") Answer answer,
			@JsonProperty("meet_uuid") String meetUuid,
			@JsonProperty("log") ArrayList<History> history,
			@JsonProperty("trade_status") TradeStatus tradeStatus) {
		this.uuid = uuid;
		this.tradeStartDateTime = tradeStartDateTime;
		this.tradeEndDateTime = tradeEndDateTime;
		this.articleOneUuid = articleOneUuid;
		this.articleTwoUuid = articleTwoUuid;
		this.answer = answer;
		this.meetUuid = meetUuid;
		this.history = history;
		this.tradeStatus = tradeStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public LocalDateTime getTradeStartDateTime() {
		return tradeStartDateTime;
	}

	public LocalDateTime getTradeEndDateTime() {
		return tradeEndDateTime;
	}

	public String getArticleOneUuid() {
		return articleOneUuid;
	}

	public String getArticleTwoUuid() {
		return articleTwoUuid;
	}

	public String getMeetUuid() {
		return meetUuid;
	}

	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public ArrayList<History> getHistory() {
		return history;
	}

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
		TradeManager.getInstance().saveDataMap();
	}
}
