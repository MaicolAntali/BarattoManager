package com.barattoManager.services.trade;

import com.barattoManager.services.json.JsonHandler;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TradeManagerTest {

	private static TradeManager tradeManagerInstance;

	@BeforeAll
	static void setUp() {
		tradeManagerInstance = new TradeManager(
				new JsonHandler<String, Trade>(
						"test/json/trades.json",
						JsonMapper.builder()
								.addModule(new ParameterNamesModule())
								.addModule(new Jdk8Module())
								.addModule(new JavaTimeModule())
								.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
								.build()
				)
						.readJson(String.class, Trade.class)
		);
	}

	@Test
	@Order(1)
	void getTradesMap() {
		assertEquals(1, tradeManagerInstance.getTradeMap().size());
	}

	@Test
	void getTradeWrongUuid() {
		assertEquals(Optional.empty(), tradeManagerInstance.getTradeByUuid("wrong-uuid"));
	}

	@Test
	void getTradeByUuid() {
		assertFalse(tradeManagerInstance.getTradeByUuid("c0715f26-0470-4709-bc91-966644e79711").isEmpty());
	}

}