package com.barattoManager.old.sample.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record representing the Field object of a category
 */
public record Field(
		@JsonProperty("name") String name,
		@JsonProperty("required") boolean required
) {
}
