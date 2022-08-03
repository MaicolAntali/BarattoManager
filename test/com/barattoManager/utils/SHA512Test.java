package com.barattoManager.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SHA512Test {

	@Test
	void hash() {
		assertEquals(
				"d8a9570f485db437a5dcd0b0ff49ba0e1b1e38c78d58046b4f4b7273dc464dd0",
				SHA256.bytesToString(SHA256.hash("BarattoManager"))
		);
	}

}