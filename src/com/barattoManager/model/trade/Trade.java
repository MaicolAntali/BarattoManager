package com.barattoManager.model.trade;

import com.barattoManager.manager.TradeManager;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
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
	@JsonProperty("meet_uuid")
	private final String meetUuid;
	@JsonProperty("trade_status")
	private TradeStatus tradeStatus;


	public Trade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {
		this.uuid = UUID.randomUUID().toString();
		this.tradeStartDateTime =  LocalDateTime.now();
		this.tradeEndDateTime = endTradeDateTime;
		this.articleOneUuid = articleOneUuid;
		this.articleTwoUuid = articleTwoUuid;
		this.meetUuid = meetUuid;
		this.tradeStatus = TradeStatus.IN_PROGRESS;
	}

	public Trade(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("trade_start_date_time") LocalDateTime tradeStartDateTime,
			@JsonProperty("trade_end_date_time") LocalDateTime tradeEndDateTime,
			@JsonProperty("article_one_uuid") String articleOneUuid,
			@JsonProperty("article_two_uuid") String articleTwoUuid,
			@JsonProperty("meet_uuid") String meetUuid,
			@JsonProperty("trade_status") TradeStatus tradeStatus) {
		this.uuid = uuid;
		this.tradeStartDateTime = tradeStartDateTime;
		this.tradeEndDateTime = tradeEndDateTime;
		this.articleOneUuid = articleOneUuid;
		this.articleTwoUuid = articleTwoUuid;
		this.meetUuid = meetUuid;
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

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
		TradeManager.getInstance().saveDataMap();
	}
}
