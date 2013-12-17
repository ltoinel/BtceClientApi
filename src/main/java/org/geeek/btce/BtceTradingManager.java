package org.geeek.btce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geeek.btce.api.BtceClientApi;
import org.geeek.btce.api.BtceClientTradeApi;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.enums.TransactionType;
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
	 * @param orders the bests orders to select
     * @return the best orders to buy or to sell
     * 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public List<Double[]> selectOrders(Pair pair, double buyAmount, List<Double[]> orders) throws BtceFunctionalException, BtceTechnicalException{
    
		List<Double[]> selection = new ArrayList<Double[]>();
		Double amount;
		
		// For each asks order buy value
		for (Iterator<Double[]> iterator = orders.iterator(); iterator.hasNext();) {
			Double[] order = iterator.next();
			amount = order[0] * order[1];

			if (buyAmount > amount){
				buyAmount = buyAmount - amount;
				selection.add(order);
				LOGGER.debug("Order all  : rate = {} - amount = {} - total = {} - pair = {}",order[0], order[1], amount, pair.name());
			} else {
				order[1] = buyAmount / order[0];
				selection.add(order);
				LOGGER.debug("Order partial : rate = {} - amount = {} - total = {} - pair = {}",order[0], order[1], buyAmount, pair.name());
				break;
			}
		}

		return selection;
    }
    
    /**
     * Buy immediate.
     * 
     * @param pair
     * @param buyAmount
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public void buyImmediate(Pair pair,double buyAmount) throws BtceFunctionalException, BtceTechnicalException{
    	
    	Depth depth = _btceClientApi.getDepth(pair);
    	
    	List<Double[]> selection = selectOrders(pair, buyAmount, depth.getAsks());
    	for (Iterator<Double[]> iterator = selection.iterator(); iterator.hasNext();) {
			 Double[] ask = iterator.next();
			_btceClientTradeApi.trade(pair, TransactionType.buy, ask[0], ask[1]);
		}
    }
    
    /**
     * Sell immediate.
     * 
     * @param pair
     * @param buyAmount
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public void sellImmediate(Pair pair,double buyAmount) throws BtceFunctionalException, BtceTechnicalException{
    	
    	Depth depth = _btceClientApi.getDepth(pair);
    	
    	List<Double[]> selection = selectOrders(pair, buyAmount, depth.getBids());
    	for (Iterator<Double[]> iterator = selection.iterator(); iterator.hasNext();) {
			 Double[] ask = iterator.next();
			_btceClientTradeApi.trade(pair, TransactionType.sell, ask[0], ask[1]);
		}
    }
    
}
