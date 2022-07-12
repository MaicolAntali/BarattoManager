package com.barattoManager.model.trade;

import com.barattoManager.manager.ArticleManager;
import com.barattoManager.manager.TradeManager;
import com.barattoManager.utils.History;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that represent a trade
 */
public class Trade {

	private static final String TRADE_RESCHEDULED_WAITING_REPLY_FROM = "Il trade è riprogrammato. Attesa di un risposta da: %s";
	private static final String TRADE_ACCEPTED_FROM_BOTH_PARTS = "Il trade è stato accettato da entrambe le parti e quindi chiuso.";
	private static final String PRE_CONDITION_TRADE_END_DATE_TIME_IS_NULL = "Pre_condition: trade end date time is null";
	private static final String PRE_CONDITION_ARTICLE_ONE_UUID_IS_BLANK = "Pre_condition: Article one UUID is blank";
	private static final String PRE_CONDITION_ARTICLE_TWO_UUID_IS_BLANK = "Pre_condition: Article two UUID is blank";
	private static final String PRE_CONDITION_MEET_UUID_IS_BLANK = "Pre_condition: Meet UUID is blank";
	private static final String PRE_CONDITION_TRADE_START_DATE_TIME_IS_NULL = "Pre-condition: Trade start date time is null";
	private static final String PRE_CONDITION_UUID_IS_BLANK = "Pre_condition: UUID is blank";
	private static final String PRE_CONDITION_HISTORY_IS_EMPTY = "Pre:condition: History is empty";
	private static final String PRE_CONDITION_TRADE_STATUS_IS_NULL = "Pre_condition: Trade status is null";
	private static final String PRE_CONDITION_ANSWER_IS_NULL = "Pre_condition: Answer is null";

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
	@JsonProperty("log")
	private final ArrayList<History> history;

	@JsonProperty("meet_uuid")
	private  String meetUuid;
	@JsonProperty("trade_status")
	private TradeStatus tradeStatus;

	/**
	 * {@link Trade} constructor
	 * @param endTradeDateTime End date and time of the trade
	 * @param articleOneUuid Article one UUID
	 * @param articleTwoUuid Article two UUID
	 * @param meetUuid Meet UUID
	 */
	public Trade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {
		Objects.requireNonNull(endTradeDateTime, PRE_CONDITION_TRADE_END_DATE_TIME_IS_NULL);
		assert !articleOneUuid.isBlank() : PRE_CONDITION_ARTICLE_ONE_UUID_IS_BLANK;
		assert !articleTwoUuid.isBlank() : PRE_CONDITION_ARTICLE_TWO_UUID_IS_BLANK;
		assert !meetUuid.isBlank() : PRE_CONDITION_MEET_UUID_IS_BLANK;

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

	/**
	 * {@link Trade} constructor
	 * @param uuid UUID
	 * @param tradeStartDateTime Start date and time of the trade
	 * @param tradeEndDateTime End date and time of the trade
	 * @param articleOneUuid Article one UUID
	 * @param articleTwoUuid Article two UUID
	 * @param answer Answer of the other user
	 * @param meetUuid Meet UUID
	 * @param history History of the Trade
	 * @param tradeStatus Trade status
	 */
	public Trade(
			@JsonProperty("uuid") String uuid,
			@JsonProperty("trade_start_date_time") LocalDateTime tradeStartDateTime,
			@JsonProperty("trade_end_date_time") LocalDateTime tradeEndDateTime,
			@JsonProperty("article_one_uuid") String articleOneUuid,
			@JsonProperty("article_two_uuid") String articleTwoUuid,
			@JsonProperty("answer") Answer answer,
			@JsonProperty("meet_uuid") String meetUuid,
			@JsonProperty("log") ArrayList<History> history,
			@JsonProperty("trade_status") TradeStatus tradeStatus)
	{

		assert !uuid.isBlank() : PRE_CONDITION_UUID_IS_BLANK;
		Objects.requireNonNull(tradeStartDateTime, PRE_CONDITION_TRADE_START_DATE_TIME_IS_NULL);
		Objects.requireNonNull(tradeEndDateTime, PRE_CONDITION_TRADE_END_DATE_TIME_IS_NULL);
		assert !articleOneUuid.isBlank() : PRE_CONDITION_ARTICLE_ONE_UUID_IS_BLANK;
		assert !articleTwoUuid.isBlank() : PRE_CONDITION_ARTICLE_TWO_UUID_IS_BLANK;
		Objects.requireNonNull(answer, PRE_CONDITION_ANSWER_IS_NULL);
		assert !meetUuid.isBlank() : PRE_CONDITION_MEET_UUID_IS_BLANK;
		assert !history.isEmpty() : PRE_CONDITION_HISTORY_IS_EMPTY;
		Objects.requireNonNull(tradeStatus, PRE_CONDITION_TRADE_STATUS_IS_NULL);


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

	/**
	 * Method used to get the UUID
	 * @return UUID
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the start date of trade
	 * @return {@link LocalDateTime} start time
	 */
	public LocalDateTime getTradeStartDateTime() {
		return tradeStartDateTime;
	}

	/**
	 * Method used to get the end date of trade
	 * @return {@link LocalDateTime} end date
	 */
	public LocalDateTime getTradeEndDateTime() {
		return tradeEndDateTime;
	}

	/**
	 * Method used to get the article one of trade
	 * @return {@link #articleOneUuid} of trade
	 */
	public String getArticleOneUuid() {
		return articleOneUuid;
	}

	/**
	 * Method used to get the article two of trade
	 * @return {@link #articleOneUuid} of trade
	 */
	public String getArticleTwoUuid() {
		return articleTwoUuid;
	}

	/**
	 * Method used to get the uuid of trade
	 * @return uuid of trade
	 */
	public String getMeetUuid() {
		return meetUuid;
	}

	/**
	 * Method used to get the {@link #tradeStatus}
	 * @return {@link #tradeStatus}
	 */
	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	/**
	 * Method used to get the {@link #answer} of trade
	 * @return {@link #answer} of trade
	 */
	public Answer getAnswer() {
		return answer;
	}

	/**
	 * Method used to get the {@link #history} of trade
	 * @return {@link #history} of trade
	 */
	public ArrayList<History> getHistory() {
		return history;
	}

	/**
	 * Method used to set a trade status
	 * @param tradeStatus the new {@link TradeStatus} to set
	 */
	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
		TradeManager.getInstance().saveDataMap();
	}

	/**
	 * Method used to reschedule a  trade
	 */
	public void rescheduleTrade() {
		getAnswer().invertWaitingUser();

		history.add(new History("Trade Riprogrammati", TRADE_RESCHEDULED_WAITING_REPLY_FROM.formatted(answer.getWaitingUserAnswer())));
		TradeManager.getInstance().saveDataMap();
	}

	/**
	 * Method used to close a trade
	 */
	public void closeTrade() {
		this.tradeStatus = TradeStatus.CLOSED;
		getAnswer().setWaitingUserAnswer(null);

		history.add(new History("Trade Chiuso", TRADE_ACCEPTED_FROM_BOTH_PARTS));

		TradeManager.getInstance().saveDataMap();
	}
}
