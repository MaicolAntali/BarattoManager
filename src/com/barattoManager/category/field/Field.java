package com.barattoManager.category.field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record that represent a field
 */
public record Field(@JsonProperty("field_name") String name, @JsonProperty("field_required") boolean required) {
}
