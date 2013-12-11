package org.geeek.btce.model;

/**
 * A Ticker on BTC-e.
 * 
 * @author Ludovic Toinel
 */
public class Ticker {

	private double high;
	private double low;
	private double avg;
	private double vol;
	private double vol_cur;
	private double last;
	private double buy;
	private double sell;
	private long updated;
	
	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}
	
	/**
	 * @param high the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}
	
	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}
	
	/**
	 * @param low the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}
	
	/**
	 * @return the avg
	 */
	public double getAvg() {
		return avg;
	}
	
	/**
	 * @param avg the avg to set
	 */
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	/**
	 * @return the vol
	 */
	public double getVol() {
		return vol;
	}
	
	/**
	 * @param vol the vol to set
	 */
	public void setVol(double vol) {
		this.vol = vol;
	}
	
	/**
	 * @return the vol_cur
	 */
	public double getVol_cur() {
		return vol_cur;
	}
	
	/**
	 * @param vol_cur the vol_cur to set
	 */
	public void setVol_cur(double vol_cur) {
		this.vol_cur = vol_cur;
	}
	
	/**
	 * @return the last
	 */
	public double getLast() {
		return last;
	}
	
	/**
	 * @param last the last to set
	 */
	public void setLast(double last) {
		this.last = last;
	}
	
	/**
	 * @return the buy
	 */
	public double getBuy() {
		return buy;
	}
	
	/**
	 * @param buy the buy to set
	 */
	public void setBuy(double buy) {
		this.buy = buy;
	}
	
	/**
	 * @return the sell
	 */
	public double getSell() {
		return sell;
	}
	
	/**
	 * @param sell the sell to set
	 */
	public void setSell(double sell) {
		this.sell = sell;
	}
	
	/**
	 * @return the updated
	 */
	public long getUpdated() {
		return updated;
	}
	
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	
}
