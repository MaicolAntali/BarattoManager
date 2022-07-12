package com.barattoManager.model.category.field;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

/**
 * Class used to deserialize the fields
 */
public class FieldDeserializer extends KeyDeserializer {
	/**
	 * Cannot read the JSON property error
	 */
	private static final String CANNOT_READ_THE_JSON_PROPERTY_ERROR = "Cannot read the JSON property";

	@Override
	public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
		var split = s
				.trim()
				.replace("Field[", "")
				.replace("]", "")
				.split(",");

		String  name;
		boolean required;
		if (split[0].contains("name=") && split[1].contains("required=")) {
			name = split[0].replace("name=", "");
			required = Boolean.parseBoolean(split[0].replace("required=", ""));
		}
		else {
			throw new IOException(CANNOT_READ_THE_JSON_PROPERTY_ERROR);
		}

		return new Field(name, required);
	}
}
