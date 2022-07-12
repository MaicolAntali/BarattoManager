package com.barattoManager.utils.parser;

import com.barattoManager.exception.IllegalValuesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringParserTest {

	@Test
	void impossibleString() {
		assertThrows(IllegalValuesException.class, () -> StringParser.stringIntoInteger("impossible"));
	}

	@Test
	void parsePositiveNumber() throws IllegalValuesException {
		assertEquals(10, StringParser.stringIntoInteger("10"));
	}

	@Test
	void parseNegativeNumber() throws IllegalValuesException {
		assertEquals(-10, StringParser.stringIntoInteger("-10"));
	}
}