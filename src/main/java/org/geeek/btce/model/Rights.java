package org.geeek.btce.model;


/**
 * BTC-e Account rights.
 * 
 * @author Ludovic Toinel
 *
 */
public class Rights {

	// Info access
	private boolean info;
	
	// Trade access
	private boolean trade;
	
	// Withdraw access
	private boolean withdraw;
	
	
	/**
	 * @return the info
	 */
	public boolean isInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(boolean info) {
		this.info = info;
	}
	/**
	 * @return the trade
	 */
	public boolean isTrade() {
		return trade;
	}
	/**
	 * @param trade the trade to set
	 */
	public void setTrade(boolean trade) {
		this.trade = trade;
	}
	/**
	 * @return the withdraw
	 */
	public boolean isWithdraw() {
		return withdraw;
	}
	/**
	 * @param withdraw the withdraw to set
	 */
	public void setWithdraw(boolean withdraw) {
		this.withdraw = withdraw;
	}
	
}
