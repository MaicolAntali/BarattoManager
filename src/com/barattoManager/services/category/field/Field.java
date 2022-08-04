package com.barattoManager.services.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Field(
		@JsonProperty("name") String name,
		@JsonProperty("required") boolean required
) {
}
