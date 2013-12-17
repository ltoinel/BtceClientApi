package org.geeek.btce.model;


/**
 * A trade transaction on BTC-e.
 * 
 * @author Ludovic Toinel
 */
public class Transaction {

	/** Amount received */
	private double received;
	
	/** Amount remains */
	private double remains;
	
	/** Order id */
	private long order_id;
	
	/** Funds status */
	private Funds funds;
	
	
	/**
	 * @return the received
	 */
	public double getReceived() {
		return received;
	}
	
	/**
	 * @param received the received to set
	 */
	public void setReceived(double received) {
		this.received = received;
	}
	
	/**
	 * @return the remains
	 */
	public double getRemains() {
		return remains;
	}
	
	/**
	 * @param remains the remains to set
	 */
	public void setRemains(double remains) {
		this.remains = remains;
	}
	
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
	 * @return the funds
	 */
	public Funds getFunds() {
		return funds;
	}
	
	/**
	 * @param funds the funds to set
	 */
	public void setFunds(Funds funds) {
		this.funds = funds;
	}
	
}
