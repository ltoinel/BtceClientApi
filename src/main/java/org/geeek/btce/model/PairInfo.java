package org.geeek.btce.model;

/**
 * Trade information for the pair.
 * 
 * @author Ludovic
 */
public class PairInfo {

	/** Number of decimal places allowed when bidding */
	double decimal_places;
	
	/** Minimum price allowed by the tender */
	double min_price;
	
	/** The maximum price allowed by the tender */
	double max_price;
	
	/** Minimum quantity allowed for purchase / sale */
	double min_amount;
	
	/** hidden whether the pair */
	boolean hidden;
	
	/** Commission couples */
	double fee;

	/**
	 * @return the decimal_places
	 */
	public double getDecimal_places() {
		return decimal_places;
	}

	/**
	 * @param decimal_places the decimal_places to set
	 */
	public void setDecimal_places(double decimal_places) {
		this.decimal_places = decimal_places;
	}

	/**
	 * @return the min_price
	 */
	public double getMin_price() {
		return min_price;
	}

	/**
	 * @param min_price the min_price to set
	 */
	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	/**
	 * @return the max_price
	 */
	public double getMax_price() {
		return max_price;
	}

	/**
	 * @param max_price the max_price to set
	 */
	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}

	/**
	 * @return the min_amount
	 */
	public double getMin_amount() {
		return min_amount;
	}

	/**
	 * @param min_amount the min_amount to set
	 */
	public void setMin_amount(double min_amount) {
		this.min_amount = min_amount;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the fee
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	
}
