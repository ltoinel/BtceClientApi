package org.geeek.btce.model;


/**
 * BTC-e Account infos
 * 
 * @author Ludovic
 */
public class AccountInfo {
	
	/** Funds on the BTC-e Account. */
	private Funds funds;
	
	/** Rights on the API. */
	private Rights rights;

	/** Transaction count. */
	private long transaction_count;
	
	/** Open orders. */
	private long open_orders;
	
	/** Server time. */
	private long server_time;

	
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

	/**
	 * @return the rights
	 */
	public Rights getRights() {
		return rights;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * @return the transaction_count
	 */
	public long getTransaction_count() {
		return transaction_count;
	}

	/**
	 * @param transaction_count the transaction_count to set
	 */
	public void setTransaction_count(long transaction_count) {
		this.transaction_count = transaction_count;
	}

	/**
	 * @return the open_orders
	 */
	public long getOpen_orders() {
		return open_orders;
	}

	/**
	 * @param open_orders the open_orders to set
	 */
	public void setOpen_orders(long open_orders) {
		this.open_orders = open_orders;
	}

	/**
	 * @return the server_time
	 */
	public long getServer_time() {
		return server_time;
	}

	/**
	 * @param server_time the server_time to set
	 */
	public void setServer_time(long server_time) {
		this.server_time = server_time;
	}
	
	
	
}
