package com.barattoManager.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record that represent a field
 */
public record Field(
		@JsonProperty("name") String name,
		@JsonProperty("required") boolean required
){}
