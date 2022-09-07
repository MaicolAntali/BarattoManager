package com.barattoManager.utils.parser;

import com.barattoManager.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringParserTest {

	@Test
	void impossibleString() {
		assertThrows(InvalidArgumentException.class, () -> StringParser.stringIntoInteger("impossible"));
	}

	@Test
	void parsePositiveNumber() throws InvalidArgumentException {
		assertEquals(10, StringParser.stringIntoInteger("10"));
	}

	@Test
	void parseNegativeNumber() throws InvalidArgumentException {
		assertEquals(-10, StringParser.stringIntoInteger("-10"));
	}

}