package com.barattoManager.model.trade;

/**
 * Enum representing the states that a {@link Trade} can have
 */
public enum TradeStatus {
	/**
	 * Trade in progress
	 */
	IN_PROGRESS,
	/**
	 * Successful Trade
	 */
	CLOSED,
	/**
	 * Trade canceled
	 */
	CANCELLED
}
