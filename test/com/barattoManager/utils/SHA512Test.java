package com.barattoManager.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SHA512Test {

	@Test
	void hash() {
		assertEquals(
				"7519fb1712ce7be99d252da42bce5255d53ffcf10c3e54399fb9d1bc022bc1db433450d5f5b9884a648769d314fdf94de0685bcf59d5aabea11bdc9a310ea846",
				SHA512.hash("BarattoManager")
		);
	}

}