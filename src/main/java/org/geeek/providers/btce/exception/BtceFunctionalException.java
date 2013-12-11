package org.geeek.providers.btce.exception;

/**
 * BTC-e Functional Exception.
 * 
 * @author Ludovic Toinel
 */
public class BtceFunctionalException extends BtceException {

	/** Default serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * @param message
	 */
	public BtceFunctionalException(String message){
		super(message);
	}
	
}
