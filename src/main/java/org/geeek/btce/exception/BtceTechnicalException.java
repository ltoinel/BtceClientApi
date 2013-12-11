package org.geeek.btce.exception;

/**
 * BTC-e Technical Exception.
 * 
 * @author Ludovic Toinel
 */
public class BtceTechnicalException extends BtceException {

	/** Default serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * @param message
	 * @param t
	 */
	public BtceTechnicalException(String message, Throwable t){
		super(message,t);
	}
}
