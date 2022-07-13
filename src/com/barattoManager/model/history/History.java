package com.barattoManager.model.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Record representing a log/history of an object<br/>
 * Usually used along with an {@link java.util.ArrayList}
 *
 * @param dateTime    {@link LocalDateTime} of the event to be recorded
 * @param name        Name of the event to be recorded
 * @param error       Description error of the event to be recorded
 * @param description Description of the event to be recorded
 */
public record History(
		@JsonProperty("date_time") LocalDateTime dateTime,
		@JsonProperty("name") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> name,
		@JsonProperty("error") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> error,
		@JsonProperty("description") @JsonInclude(JsonInclude.Include.NON_ABSENT) Optional<String> description) {

	/**
	 * Constructor of the record
	 *
	 * @param name        Name of the event to be recorded
	 * @param description Description of the event to be recorded
	 */
	public History(String name, String description) {
		this(name, description, false);
	}

	/**
	 * Constructor of the record
	 *
	 * @param name        Name of the event to be recorded
	 * @param description Description of the event to be recorded
	 * @param isError     True if is an error otherwise false
	 */
	public History(String name, String description, boolean isError) {
		this(LocalDateTime.now(), isError ? Optional.empty() : Optional.of(name), isError ? Optional.of(name) : Optional.empty(), Optional.of(description));
	}
}
