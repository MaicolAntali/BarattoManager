package com.barattoManager.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
	@JsonProperty("field_name")
	private final String name;
	@JsonProperty("field_required")
	private final boolean required;

	public Field(
			@JsonProperty("field_name") String name,
			@JsonProperty("field_required") boolean required
	) {
		this.name = name;
		this.required = required;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}
}
