package org.geeek.btce.model;

import java.util.List;

/**
 * Depth list.
 * 
 * @author Ludovic Toinel
 */
public class Depth {

	// Asks list 
	private List<Double[]> asks;
	
	// Bids list 
	private List<Double[]> bids;

	/**
	 * @return the asks
	 */
	public List<Double[]> getAsks() {
		return asks;
	}

	/**
	 * @param asks the asks to set
	 */
	public void setAsks(List<Double[]> asks) {
		this.asks = asks;
	}

	/**
	 * @return the bids
	 */
	public List<Double[]> getBids() {
		return bids;
	}

	/**
	 * @param bids the bids to set
	 */
	public void setBids(List<Double[]> bids) {
		this.bids = bids;
	}

}
