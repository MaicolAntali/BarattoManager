package com.barattoManager.category.field;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

public class FieldDeserializer extends KeyDeserializer {
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
			name = split[0].replace("name=","");
			required = Boolean.parseBoolean(split[0].replace("required=", ""));
	    }
		else {
			throw new IOException("Cannot read the JSON property");
		}

		return new Field(name, required);
	}
}
