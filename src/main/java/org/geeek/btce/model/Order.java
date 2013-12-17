package org.geeek.btce.model;

import org.geeek.btce.enums.Pair;
import org.geeek.btce.enums.TransactionType;

/**
 * An Order entry on BTC-e.
 * 
 * @author Ludovic Toinel
 */
public class Order {
	
	/** Order Id. */
	private long order_id;
	
	/** Order pair. */
	private Pair pair;
	
	/** Transaction type. */
	private TransactionType type;
	
	/** Amount of the order. */
	private double amount;
	
	/** Rate of the order. */
	private double rate;
	
	/** Timestamp when the order has been created. */
	private long timestamp_created;
	
	/** Order status. */
	private int status;

	/** True if this order is yours. */
	private boolean is_your_order;
	
	/** Timestamp. */
	private long timestamp;
	
	/** Currency used. */
	private String currency;
	
	/** Description. */
	private String desc;

	/**
	 * @return the order_id
	 */
	public long getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return the pair
	 */
	public Pair getPair() {
		return pair;
	}

	/**
	 * @param pair the pair to set
	 */
	public void setPair(Pair pair) {
		this.pair = pair;
	}

	/**
	 * @return the type
	 */
	public TransactionType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TransactionType type) {
		this.type = type;
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
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the timestamp_created
	 */
	public long getTimestamp_created() {
		return timestamp_created;
	}

	/**
	 * @param timestamp_created the timestamp_created to set
	 */
	public void setTimestamp_created(long timestamp_created) {
		this.timestamp_created = timestamp_created;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the is_your_order
	 */
	public boolean isIs_your_order() {
		return is_your_order;
	}

	/**
	 * @param is_your_order the is_your_order to set
	 */
	public void setIs_your_order(boolean is_your_order) {
		this.is_your_order = is_your_order;
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

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
