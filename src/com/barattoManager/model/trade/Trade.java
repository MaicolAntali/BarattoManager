package com.barattoManager.model.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record Trade(
		@JsonProperty("uuid") String uuid,
		@JsonProperty("trade_start_date_time") LocalDateTime tradeStartDateTime,
		@JsonProperty("trade_end_date_time") LocalDateTime tradeEndDateTime,
		@JsonProperty("article_one_uuid") String articleOneUuid,
		@JsonProperty("article_two_uuid") String articleTwoUuid,
		@JsonProperty("meet_uuid") String meetUuid
		)
{
	public Trade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {
		this(UUID.randomUUID().toString(), LocalDateTime.now(), endTradeDateTime, articleOneUuid, articleTwoUuid, meetUuid);
	}
}
