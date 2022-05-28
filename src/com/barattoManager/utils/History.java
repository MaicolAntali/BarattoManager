package com.barattoManager.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Optional;

public record History(
		@JsonProperty("date_time") LocalDateTime dateTime,
		@JsonProperty("name") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> name,
		@JsonProperty("error") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> error,
		@JsonProperty("description") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> description) {

	public History(String name, String description) {
		this(LocalDateTime.now(), Optional.of(name), Optional.empty(), Optional.of(description));
	}

	public History(String error, String description, boolean isError) {
		this(LocalDateTime.now(), Optional.empty(), Optional.of(error), Optional.of(description));
	}
}
