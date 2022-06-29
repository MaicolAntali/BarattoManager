package com.barattoManager.model.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {
	@JsonProperty("user_one")
	private final String userOne;
	@JsonProperty("user_two")
	private final String userTwo;

	@JsonProperty("waiting_user")
	private String waitingUserAnswer;


	public Answer(String userOne, String userTwo) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.waitingUserAnswer = userOne;
	}

	public Answer(
			@JsonProperty("user_one") String userOne,
			@JsonProperty("user_two") String userTwo,
			@JsonProperty("waiting_user") String waitingUserAnswer
	) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.waitingUserAnswer = waitingUserAnswer;
	}

	public void invertWaitingUser() {
		if (this.waitingUserAnswer.equals(userOne))
			this.waitingUserAnswer = userTwo;
		else
			this.waitingUserAnswer = userOne;
	}

	public String getWaitingUserAnswer() {
		return waitingUserAnswer;
	}
}
