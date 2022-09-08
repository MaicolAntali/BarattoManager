package com.barattoManager.services.trade.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represent an answer object
 */
public class Answer {

	@JsonProperty("user_one")
	private final String userOne;
	@JsonProperty("user_two")
	private final String userTwo;
	@JsonProperty("waiting_user")
	private String waitingUserAnswer;

	/**
	 * Constructor of the clas
	 *
	 * @param userOne Username one of the user involved in the trade
	 * @param userTwo Username two of the user involved in the trade
	 */
	public Answer(String userOne, String userTwo) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.waitingUserAnswer = userOne;
	}

	/**
	 * Constructor of the clas
	 *
	 * @param userOne           Username one of the user involved in the trade
	 * @param userTwo           Username two of the user involved in the trade
	 * @param waitingUserAnswer Username of the user waiting for an answer
	 */
	public Answer(
			@JsonProperty("user_one") String userOne,
			@JsonProperty("user_two") String userTwo,
			@JsonProperty("waiting_user") String waitingUserAnswer
	) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.waitingUserAnswer = waitingUserAnswer;
	}

	/**
	 * Method used to invert the username of the user waiting for an answer
	 */
	public void invertWaitingUser() {
		if (this.waitingUserAnswer.equals(userOne))
			this.waitingUserAnswer = userTwo;
		else
			this.waitingUserAnswer = userOne;
	}

	/**
	 * Method used to set the username of the user waiting for an answer
	 *
	 * @param waitingUserAnswer username of the user waiting for an answer to set
	 */
	public void setWaitingUserAnswer(String waitingUserAnswer) {
		this.waitingUserAnswer = waitingUserAnswer;
	}

	/**
	 * Method used to get the username of the user waiting for an answer
	 *
	 * @return Username of the user waiting for an answer
	 */
	public String getWaitingUserAnswer() {
		return waitingUserAnswer;
	}
}
