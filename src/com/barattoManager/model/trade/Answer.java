package com.barattoManager.model.trade;

import com.barattoManager.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used to represent the answer of a trade
 */
public class Answer {
	/**
	 * User one, who is involved in the trade
	 */
	@JsonProperty("user_one")
	private final String userOne;
	/**
	 * User two, who is involved in the trade
	 */
	@JsonProperty("user_two")
	private final String userTwo;

	/**
	 * User who is waiting for an answer
	 */
	@JsonProperty("waiting_user")
	private String waitingUserAnswer;

	/**
	 * {@link Answer} constructor
	 * @param userOne User one, who is involved in the trade
	 * @param userTwo User two, who is involved in the trade
	 */
	public Answer(String userOne, String userTwo) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.waitingUserAnswer = userOne;
	}

	/**
	 * {@link Answer} constructor
	 * @param userOne User one, who is involved in the trade
	 * @param userTwo User two, who is involved in the trade
	 * @param waitingUserAnswer User who is waiting for an answer
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
	 * Method used to invert the user who is waiting for the answer
	 */
	public void invertWaitingUser() {
		if (this.waitingUserAnswer.equals(userOne))
			this.waitingUserAnswer = userTwo;
		else
			this.waitingUserAnswer = userOne;
	}

	/**
	 * Method used to set the waiting user answer
	 * @param waitingUserAnswer User to set
	 */
	public void setWaitingUserAnswer(String waitingUserAnswer) {
		this.waitingUserAnswer = waitingUserAnswer;
	}

	/**
	 * Method used to get the waiting user answer
	 * @return WaitingUserAnswer
	 */
	public String getWaitingUserAnswer() {
		return waitingUserAnswer;
	}
}
