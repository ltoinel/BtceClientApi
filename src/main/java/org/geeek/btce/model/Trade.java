package org.geeek.btce.model;

import org.geeek.btce.enums.TradeType;

/**
 * A Trade on BTC-e.
 * 
 * @author Ludovic Toinel
 */
public class Trade {

	/** The trade type. */
	private TradeType type;
	
	/** The trade price. */
	private double price;
	
	/** The trade amount. */
	private double amount;
	
	/** The trade id. */
	private double tid;
	
	/** The trade timestamp. */
	private long timestamp;

	/**
	 * @return the type
	 */
	public TradeType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TradeType type) {
		this.type = type;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the tid
	 */
	public double getTid() {
		return tid;
	}

	/**
	 * @param tid the tid to set
	 */
	public void setTid(double tid) {
		this.tid = tid;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	

}
