package com.barattoManager.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Record that represent a field
 */
public record Field(
		@JsonProperty("name") String name,
		@JsonProperty("required") boolean required
){}
