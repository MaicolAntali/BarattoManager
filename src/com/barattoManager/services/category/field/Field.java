package com.barattoManager.services.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record that represent the field object of a category
 *
 * @param name     Name of the field
 * @param required Is true if the field is necessarily required for the category
 */
public record Field(
		@JsonProperty("name") String name,
		@JsonProperty("required") boolean required
) {
}
