package org.geeek.btce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geeek.btce.api.BtceClientApi;
import org.geeek.btce.api.BtceClientTradeApi;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.Depth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business logic for BTC-e trading.
 * 
 * @author Ludovic Toinel
 */
public class BtceTradingManager {
	
	// Logger
	private final static Logger LOGGER = LoggerFactory.getLogger(BtceClientApi.class);
	
	// Public API client
	private BtceClientApi _btceClientApi;

	// Private API client
	private BtceClientTradeApi _btceClientTradeApi;

	/**
	 * Default constructor.
	 * 
	 * @param key the trade api key.
	 * @param secret the trade api secret.
	 */
	public BtceTradingManager(String key,String secret){
		_btceClientApi = new BtceClientApi();
		_btceClientTradeApi = new BtceClientTradeApi(key,secret);
	}
	
	
	/**
     * This method returns the best asks at the lowest rates for an amount.
     * 
     * @param pair the pair
     * @param buyAmount the amount to buy
     * @return the best asks for order at the 
     * 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public List<Double[]> getBestAsksForOrder(Pair pair,double buyAmount) throws BtceFunctionalException, BtceTechnicalException{
    
		Depth depth = _btceClientApi.getDepth(pair);
		
		// Get the asks list to extract prices
		List<Double[]> asks = depth.getAsks();
		
		List<Double[]> orders = new ArrayList<Double[]>();
		Double amount;
		
		// For each asks order buy value
		for (Iterator<Double[]> iterator = asks.iterator(); iterator.hasNext();) {
			Double[] ask = iterator.next();
			amount = ask[0] * ask[1];

			if (buyAmount > amount){
				buyAmount = buyAmount - amount;
				orders.add(ask);
				LOGGER.debug("Order all  : rate = {} - amount = {} - total = {} - pair = {}",ask[0], ask[1], amount, pair.name());
			} else {
				ask[1] = buyAmount / ask[0];
				orders.add(ask);
				LOGGER.debug("Order partial : rate = {} - amount = {} - total = {} - pair = {}",ask[0], ask[1], buyAmount, pair.name());
				break;
			}
		}

		return orders;
    }
    
}
