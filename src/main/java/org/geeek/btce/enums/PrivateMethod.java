package org.geeek.btce.enums;

/**
 * List of the available methods on the private BTC-e API.
 * 
 * @author Ludovic Toinel
 *
 */
public enum PrivateMethod {
	
	/** Cancel an order */
	CancelOrder,
	
	/** Make a trade */
	Trade,
	
	/** Retrieve active orders */
	ActiveOrders,
	
	/** Get the trade history*/
	TradeHistory,
	
	/** Get the transaction history*/
	TransHistory,
	
	/** Get the account information */
	getInfo;
}
