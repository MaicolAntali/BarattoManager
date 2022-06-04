package com.barattoManager.trade;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TradeManagerTest {

	@Test
	void testChecker() {
		var instance = TradeManager.getInstance();

		instance.addNewTrade(LocalDateTime.now().plusMinutes(2), "15bb4d3e-8979-4152-a7d8-629c903ec8e6", "49ccbdeb-8816-4930-bf4e-aee63c934b59");

	}
}