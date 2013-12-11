package org.geeek.providers.btce.exception;

/**
 * Abstract BTC-e Exception.
 * 
 * @author Ludovic Toinel
 */
public abstract class BtceException extends Exception{
	
	/** Default serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * @param message
	 */
	public BtceException(String message){
		super(message);
	}
	
	/**
	 * Default constructor.
	 * @param message
	 * @param t
	 */
	public BtceException(String message, Throwable t){
		super(message,t);
	}
}
