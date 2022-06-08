package com.barattoManager.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Utility record of history
 *
 * @param dateTime Date and time of history
 * @param name Name of history
 * @param error Description of history
 * @param description Description of history
 */
public record History(
		@JsonProperty("date_time") LocalDateTime dateTime,
		@JsonProperty("name") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> name,
		@JsonProperty("error") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> error,
		@JsonProperty("description") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> description) {

	/**
	 * {@link History} constructor
	 * @param name Name of history
	 * @param description Description of history
	 */
	public History(String name, String description) {
		this(LocalDateTime.now(), Optional.of(name), Optional.empty(), Optional.of(description));
	}

	/**
	 * {@link History} constructor
	 * @param error Error of history
	 * @param description Description of history
	 * @param isError true if is error otherwise false
	 */
	public History(String error, String description, boolean isError) {
		this(LocalDateTime.now(), Optional.empty(), Optional.of(error), Optional.of(description));
	}
}
