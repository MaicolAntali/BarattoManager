package com.barattoManager.services.trade;

import com.barattoManager.services.article.ArticleManagerFactory;
import com.barattoManager.services.history.History;
import com.barattoManager.services.trade.answer.Answer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Class that represent a trade object
 */
public class Trade {
	private static final String PRE_CONDITION_TRADE_END_DATE_TIME_IS_NULL = "Pre_condition: trade end date time is null";
	private static final String PRE_CONDITION_ARTICLE_ONE_UUID_IS_BLANK = "Pre_condition: Article one UUID is blank";
	private static final String PRE_CONDITION_ARTICLE_TWO_UUID_IS_BLANK = "Pre_condition: Article two UUID is blank";
	private static final String PRE_CONDITION_MEET_UUID_IS_BLANK = "Pre_condition: Meet UUID is blank";
	private static final String PRE_CONDITION_TRADE_START_DATE_TIME_IS_NULL = "Pre-condition: Trade start date time is null";
	private static final String PRE_CONDITION_UUID_IS_BLANK = "Pre_condition: UUID is blank";
	private static final String PRE_CONDITION_HISTORY_IS_EMPTY = "Pre:condition: History is empty";
	private static final String PRE_CONDITION_TRADE_STATUS_IS_NULL = "Pre_condition: Trade status is null";
	private static final String PRE_CONDITION_ANSWER_IS_NULL = "Pre_condition: Answer is null";
	private static final String SUCCESSFUL_EXCHANGE_CREATION = "Creazione dello scambio avvenuta con successo";
	private static final String TRADE_REPROGRAMMED_WAITING_REPLY = "Il trade è riprogrammato. Attesa di un risposta da: %s";
	private static final String TRADE_CLOSED = "Il trade è stato accettato da entrambe le parti e quindi chiuso.";

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
	private String meetUuid;
	@JsonProperty("trade_status")
	private TradeStatus tradeStatus;

	/**
	 * Constructor of the class
	 *
	 * @param endTradeDateTime {@link LocalDateTime} Expiry date of the trade
	 * @param articleOneUuid   Uuid of the article one involved in the trade
	 * @param articleTwoUuid   Uuid of the article two involved in the trade
	 * @param meetUuid         Uuid of the meet
	 */
	public Trade(LocalDateTime endTradeDateTime, String articleOneUuid, String articleTwoUuid, String meetUuid) {
		Objects.requireNonNull(endTradeDateTime, PRE_CONDITION_TRADE_END_DATE_TIME_IS_NULL);
		assert !articleOneUuid.isBlank() : PRE_CONDITION_ARTICLE_ONE_UUID_IS_BLANK;
		assert !articleTwoUuid.isBlank() : PRE_CONDITION_ARTICLE_TWO_UUID_IS_BLANK;
		assert !meetUuid.isBlank() : PRE_CONDITION_MEET_UUID_IS_BLANK;

		this.uuid = UUID.randomUUID().toString();
		this.tradeStartDateTime = LocalDateTime.now();
		this.tradeEndDateTime = endTradeDateTime;
		this.articleOneUuid = articleOneUuid;
		this.articleTwoUuid = articleTwoUuid;
		this.answer = new Answer(
				ArticleManagerFactory.getManager().getArticleById(articleOneUuid).orElseThrow(NullPointerException::new).getUserNameOwner(),
				ArticleManagerFactory.getManager().getArticleById(articleTwoUuid).orElseThrow(NullPointerException::new).getUserNameOwner()
		);
		this.meetUuid = meetUuid;
		this.history = new ArrayList<>();
		this.tradeStatus = TradeStatus.IN_PROGRESS;

		history.add(new History("Scambio creato", SUCCESSFUL_EXCHANGE_CREATION));
	}

	/**
	 * Constructor of the class
	 *
	 * @param uuid               uuid of the trade
	 * @param tradeStartDateTime {@link LocalDateTime} Date of trade initiation
	 * @param tradeEndDateTime   {@link LocalDateTime} Expiry date of the trade
	 * @param articleOneUuid     Uuid of the article one involved in the trade
	 * @param articleTwoUuid     Uuid of the article one involved in the trade
	 * @param answer             {@link Answer} of the trade
	 * @param meetUuid           Uuid of the meet
	 * @param history            {@link ArrayList} of the {@link History}
	 * @param tradeStatus        {@link TradeStatus}
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
			@JsonProperty("trade_status") TradeStatus tradeStatus) {

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
	 * Method used to get the uuid of the trade
	 *
	 * @return Uuid of the trade
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Method used to get the {@link LocalDateTime} Date of trade initiation of the trade
	 *
	 * @return {@link LocalDateTime} Date of trade initiation of the trade
	 */
	public LocalDateTime getTradeStartDateTime() {
		return tradeStartDateTime;
	}

	/**
	 * Method used to get the {@link LocalDateTime} Expiry date of the trade of the trade
	 *
	 * @return {@link LocalDateTime} Expiry date of the trade of the trade
	 */
	public LocalDateTime getTradeEndDateTime() {
		return tradeEndDateTime;
	}

	/**
	 * Method used to get the uuid of the article one involved in the trade
	 *
	 * @return Uuid of the article one involved in the trade
	 */
	public String getArticleOneUuid() {
		return articleOneUuid;
	}

	/**
	 * Method used to get the uuid of the article two involved in the trade
	 *
	 * @return Uuid of the article two involved in the trade
	 */
	public String getArticleTwoUuid() {
		return articleTwoUuid;
	}

	/**
	 * Method used to get the uuid of meet
	 *
	 * @return Uuid of meet
	 */
	public String getMeetUuid() {
		return meetUuid;
	}

	/**
	 * Method used to get the {@link TradeStatus} of the trade
	 *
	 * @return {@link TradeStatus} of the trade
	 */
	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	/**
	 * Method used to get the {@link Answer} of trade
	 *
	 * @return {@link Answer} of trade
	 */
	public Answer getAnswer() {
		return answer;
	}

	/**
	 * Method used to get the {@link ArrayList} of the {@link History}
	 *
	 * @return {@link ArrayList} of the {@link History}
	 */
	public ArrayList<History> getHistory() {
		return history;
	}

	/**
	 * Method used to set the {@link TradeStatus}
	 *
	 * @param tradeStatus {@link TradeStatus} to set
	 */
	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
		TradeManagerFactory.getManager().saveData();
	}

	/**
	 * Method used to reschedule a trade
	 */
	public void rescheduleTrade() {
		getAnswer().invertWaitingUser();

		history.add(new History("Trade Riprogrammati", TRADE_REPROGRAMMED_WAITING_REPLY.formatted(answer.getWaitingUserAnswer())));
		TradeManagerFactory.getManager().saveData();
	}

	/**
	 * Method used to close trade
	 */
	public void closeTrade() {
		this.tradeStatus = TradeStatus.CLOSED;
		getAnswer().setWaitingUserAnswer(null);

		history.add(new History("Trade Chiuso", TRADE_CLOSED));

		TradeManagerFactory.getManager().saveData();
	}

}
