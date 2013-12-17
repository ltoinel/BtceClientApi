package org.geeek.btce;

import java.util.Iterator;
import java.util.List;

import org.geeek.btce.api.BtceClientApi;
import org.geeek.btce.api.BtceClientTradeApi;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.enums.TransactionType;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.AccountInfo;
import org.geeek.btce.model.Depth;
import org.geeek.btce.model.Funds;
import org.geeek.btce.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business logic for BTC-e trading. (In development)
 * 
 * @author Ludovic Toinel
 */
public class BtceTradingManager {
	
	/** Logger **/
	private final static Logger LOGGER = LoggerFactory.getLogger(BtceTradingManager.class);
	
	/** Public API client */
	private BtceClientApi _btceClientApi;

	/** Private API client */
	private BtceClientTradeApi _btceClientTradeApi;
	
	/** Current Funds */
	private Funds _funds;
	
	/** Open orders */
	private long _openOrders;
	
	/** Transaction count */
	private long _transactionCount;

	/**
	 * Default constructor.
	 * 
	 * @param key the trade api key.
	 * @param secret the trade api secret.
	 * 
	 * @throws BtceFunctionalException 
	 * @throws BtceTechnicalException 
	 */
	public BtceTradingManager(String key,String secret) throws BtceTechnicalException, BtceFunctionalException{
		_btceClientApi = new BtceClientApi();
		_btceClientTradeApi = new BtceClientTradeApi(key,secret);
		
		// Get the account information
		AccountInfo accountInfo = _btceClientTradeApi.getAccountInfo();
		_funds = accountInfo.getFunds();
		_openOrders = accountInfo.getOpen_orders();
		_transactionCount = accountInfo.getTransaction_count();
		
		// Check the trade right
		if (!accountInfo.getRights().isTrade()){
			throw new BtceFunctionalException("The API access is not allowed for trading");
		}
		
		// Check the info right
		if (!accountInfo.getRights().isInfo()){
			throw new BtceFunctionalException("The API access is not allowed for information access");
		}
	}
	
	
	/**
     * This method returns the best asks at the lowest rates for an amount.
     * 
     * @param pair the pair
     * @param buyAmount the amount to buy or to sell
     * @return true if the amount has been sold or bought
     * 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    private boolean createOrders(Pair pair, double amount, TransactionType transactionType) throws BtceFunctionalException, BtceTechnicalException{
    
    	Depth depth = _btceClientApi.getDepth(pair);
    	List<Double[]> orders = null;
    	
    	// Get the asks or bids
    	if (TransactionType.buy.equals(transactionType)){
    		orders = depth.getAsks();
    	} else if (TransactionType.sell.equals(transactionType)){
    		orders = depth.getBids();
    	}
		
		Double orderAmount;
		
		// For each 
		for (Iterator<Double[]> iterator = orders.iterator(); iterator.hasNext();) {
			Double[] order = iterator.next();
			orderAmount = order[0] * order[1];

			// If the amount is less than the order amount
			if (amount < orderAmount){
				order[1] = amount / order[0];
			}
			
			// We trade the amount with the order rate
			Transaction transaction = trade(pair, transactionType, order[0], order[1]);
			double remains = transaction.getRemains();
			
			amount = amount - orderAmount;
			
			// if there are remains
			if (remains > 0){

				// We cancel the order immediately
				if (cancelOrder(transaction.getOrder_id())){
					amount = amount + remains;
				} else {
					// The order has been trade, no remains.
				}
			}
			
			LOGGER.info("Order  : rate = {} - amount = {} - total = {} - remains = {} - pair = {}",order[0], order[1], amount, remains, pair.name());

			if (amount == 0){
				break;
			}
		}

		return true;
    }
    
    /**
     * Cancel an order.
     * 
     * @param order_id
     * @return true if the order has been canceled.
     * @throws BtceTechnicalException 
     */
    private boolean cancelOrder(long order_id) throws BtceTechnicalException {
    	try {
			Transaction transaction = _btceClientTradeApi.cancelOrder(order_id);
			_funds = transaction.getFunds();
			_openOrders--;
			return true;
		} catch (BtceFunctionalException e) {
			return false;
		}
	}


	/**
     * 
     * @param pair
     * @param transactionType
     * @param rate
     * @param amount
     * @return the transaction
     * @throws BtceTechnicalException
     * @throws BtceFunctionalException
     */
    public Transaction trade(Pair pair, TransactionType transactionType, Double rate, Double amount) throws BtceTechnicalException, BtceFunctionalException{

    	if (checkFunds(pair,transactionType,rate,amount)){
    		Transaction transaction = _btceClientTradeApi.trade(pair, transactionType, rate, amount);
    		_funds = transaction.getFunds();
    		
    		// An order has been placed.
    		if (transaction.getOrder_id() != 0){
    			_openOrders++;
    		}
    		
    		return transaction;
    	}
    	
    	throw new BtceFunctionalException("Not enough fund"); 
    }
    
    /**
     * Check if the fund is enough.
     * 
     * @param pair
     * @param transactionType
     * @param rate
     * @param amount
     * @return
     * @throws BtceFunctionalException 
     */
    private boolean checkFunds(Pair pair, TransactionType transactionType,
			Double rate, Double amount) throws BtceFunctionalException {
		
    	double fund = getFund(pair);
    	
    	// Check the fund for buying
    	if (TransactionType.buy.equals(transactionType)){
    		
    		if (fund >= (rate * amount)){
    			LOGGER.debug("Fund for {} is enough for buying {} coins with rate {} ", pair.name(), amount, rate);
    			return true;
    		}
    	
    	// Check the fund for selling
    	} else if (TransactionType.sell.equals(transactionType)){
    		
    		if (fund >= amount){
    			LOGGER.debug("Fund for {} is enough for selling {} coins", pair.name(), amount);
    			return true;
    		}
    	}
    	
    	LOGGER.debug("Fund for {} is not enough");
		return false;
	}


    /**
     * Get the fund for a pair.
     * 
     * @param pair the pair
     * @return the fund
     * @throws BtceFunctionalException
     */
	private double getFund(Pair pair) throws BtceFunctionalException {
		
		double fund = 0;
    	
    	if (pair.isBtc()){
    		fund  = _funds.getBtc();
    	} else if (pair.isEur()){
   		 	fund  = _funds.getEur();
    	} else if (pair.isFtc()){
   		 	fund  = _funds.getFtc();
    	} else if (pair.isLtc()){
   		 	fund  = _funds.getLtc();
    	} else if (pair.isNmc()){
    		fund  = _funds.getNmc();
    	} else if (pair.isNvc()){
    		fund  = _funds.getNvc();
    	} else if (pair.isPpc()){
    		fund  = _funds.getPpc();
    	} else if (pair.isTrc()){
    		fund  = _funds.getTrc();
    	}else if (pair.isUsd()){
    		fund  = _funds.getUsd();
    	}else if (pair.isXpm()){
    		fund  = _funds.getXpm();
    	} else {
    		throw new BtceFunctionalException("Unknown fund");
    	}
    	
    	LOGGER.debug("Fund for {} : {}", pair.name(), fund);
    	
    	return fund;
	}


	/**
     * Buy immediate.
     * 
     * @param pair the pair to buy.
     * @param amount the amount to buy.
     * @return true if the amount has been bought.
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public boolean buyImmediate(Pair pair,double amount) throws BtceFunctionalException, BtceTechnicalException{

    	return createOrders(pair, amount, TransactionType.buy);
    }
    
    /**
     * Sell immediate.
     * 
     * @param pair the pair to sold.
     * @param amount the amount to sold.
     * @return true if the amount has been sold.
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public boolean sellImmediate(Pair pair,double amount) throws BtceFunctionalException, BtceTechnicalException{
    	
    	return createOrders(pair, amount, TransactionType.sell);
    }
    
}
