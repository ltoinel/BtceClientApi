package org.geeek.btce.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.geeek.btce.enums.ListOrder;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.enums.PrivateMethod;
import org.geeek.btce.enums.TransactionType;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.AccountInfo;
import org.geeek.btce.model.Order;
import org.geeek.btce.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Java Client for BTC-e trade APIs.
 * 
 * @see "https://btc-e.com/api/documentation"
 * @author Ludovic Toinel
 * @version 1.0.0
 */
public class BtceClientTradeApi {

	// Logger
	private final static Logger LOGGER = LoggerFactory.getLogger(BtceClientTradeApi.class);
	
	// Server URI
	private final static String API_URI = "https://btc-e.com/tapi/";
	
	// Nonce token  
	private long _nonce = 1;

	// Private API Key
	private String _key;
	
	// Secret API key
	private String _secret;
	
	// Mac
	private Mac _mac;
	
	// JSON Object Mapper
	private ObjectMapper _mapper;

	/**
	 * Default constructor
	 * 
	 * @param key The API key.
	 * @param secret The secret key.
	 */
	public BtceClientTradeApi(String key, String secret){
		this._key = key;
		this._secret = secret;
		this._mapper = new ObjectMapper();
		initKey();
	}
	
	/** 
	 * Init key method
	 */
	private void initKey(){

		SecretKeySpec key = null;
		
		// Create a new secret key
		try {
		    key = new SecretKeySpec( _secret.getBytes( "UTF-8"), "HmacSHA512" ); 
		} catch( UnsupportedEncodingException uee) {
			LOGGER.error("HmacSHA512 encoding not supported",uee);
		} 

		// Create a new mac
		try {
		    _mac = Mac.getInstance( "HmacSHA512" );
		} catch( NoSuchAlgorithmException nsae) {
			LOGGER.error("No such HmacSHA512 algorithm exception",nsae);
		}

		// Init mac with key.
		try {
		    _mac.init( key);
		} catch( InvalidKeyException ike) {
			LOGGER.error( "Invalid key exception",ike);
		}
	}
	
	/**
	 * Synchronized Nonce generator to avoid that two requests have the same nonce.
	 * 
	 * @return The increased nonce value.
	 */
	private synchronized String getNonce(){
		return Long.toString(_nonce++);
	}
	
	
    /**
     * Execute an HTTP query on the BTC-e private API.
     *
     * @param method The method to execute.
     * @param arguments The arguments to pass to the server.
     *
     * @return The returned data as JSON or null, if the request failed.
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     * @see "http://pastebin.com/K25Nk2Sv"
     */
    private final JSONObject authenticatedHTTPRequest(PrivateMethod method, Map<String, String> arguments) throws BtceTechnicalException, BtceFunctionalException {
    	 
    	// Create a new map for the header lines.
    	List<Header> headers = new ArrayList<Header>();
    	 
		// If the user provided no arguments, just create an empty argument array.
		if( arguments == null) { 
		    arguments = new HashMap<String, String>();
		}
		
		arguments.put( "method", method.name());  // Add the method to the post data.
		arguments.put( "nonce", getNonce());  // Add the dummy nonce.

		// Generate the post Data from arguments
		String postData = getPostData(arguments);

		// Add the key to the header lines.
		headers.add(new BasicHeader("Key", _key));

		// Encode the post data by the secret and encode the result as base64.
		try {
			headers.add(new BasicHeader("Sign", signRequest(postData)));
		} catch( UnsupportedEncodingException uee) {
		    System.err.println( "Unsupported encoding exception: " + uee.toString());
		    return null;
		} 
		
		try {
			LOGGER.debug("Private HTTP Request : " + API_URI);
			Response response = Request.Post(API_URI)
										.setHeaders(headers.toArray(new Header[0]))
										.bodyString(postData, ContentType.APPLICATION_FORM_URLENCODED).execute();

			Content content = response.returnContent();
			
			if( content != null) {  

				LOGGER.debug("Received message : {}", content.asString());
				
				// Convert the HTTP request return value to JSON to parse further.
				JSONObject jsonResult = JSONObject.fromObject(content.asString());
	
				// Check, if the request was successful
				int success = jsonResult.getInt("success");
	
				if( success == 1) {  
					
					// Request succeeded!
				    return jsonResult.getJSONObject("return");
	
				} else {  
					
					// The request failed.
					String errorMessage = jsonResult.getString("error");
					
					if (checkNonceError(errorMessage)){
						// We retry with the good nonce value
						return authenticatedHTTPRequest(method, arguments);
					}
					
					LOGGER.error("BTC-e API request failed : {}", errorMessage);
					throw new BtceFunctionalException(errorMessage);
				}
			} 
			
		} catch (ClientProtocolException e) {
			LOGGER.error("Protocol error while connecting to BTC-e API" , e);
			throw new BtceTechnicalException("Protocol error",e);
		} catch (IOException e) {
			LOGGER.error("IO error while connecting to BTC-e API" , e);
			throw new BtceTechnicalException("IO error",e);
		} catch( JSONException je) {
			LOGGER.error("JSON Error with BTC-e API" , je);
			throw new BtceTechnicalException("JSON Error",je);
	    }

		return null;
    }

    /**
     * Check if the error come from the nonce value.
     * 
     * @param errorMessage
     * @return
     */
	private boolean checkNonceError(String errorMessage) {
		
		// If nonce error, we increment the once value
		Pattern r  = Pattern.compile("invalid nonce parameter; .+, you should send:(\\d+)");
		Matcher m = r.matcher(errorMessage);
				
		if (m.find()){
			LOGGER.warn("Invalid nonce parameter, we use the good one");
			_nonce = Long.parseLong(m.group(1));
			return true;
		}
		
		return false;
	}
	    
    /**
     * Generate post data from arguments.
     * 
     * @param arguments The list of arguments.
     * @return The post data.
     */
	private static String getPostData(Map<String, String> arguments) {
		
		String postData = "";
	
		// For each argument
		for( Iterator<Map.Entry<String,String>> argumentIterator = arguments.entrySet().iterator(); argumentIterator.hasNext(); ) {
		    Map.Entry<String,String> argument = argumentIterator.next();
		    if (argument.getValue() != null){
			    
			    if( postData.length() > 0) {
			    	postData += "&";
			    }
			    postData += argument.getKey() + "=" + argument.getValue();
			}
		}
		LOGGER.debug("Post data : {}", postData);
		return postData;
	}
	    
	/**
	 * Sign the data of an HTTP Request.
	 * 
	 * @param postData The post data.
	 * @return The signature.
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalStateException 
	 */
    private String signRequest(String postData) throws IllegalStateException, UnsupportedEncodingException{
		
		String signature = Hex.encodeHexString( _mac.doFinal( postData.getBytes( "UTF-8")));
		LOGGER.debug("Data signature : {}", signature);
		
		return signature;
	}
	    
    
    ///////////////////////////////////////////////////////////////////////////////////
    // ACTION METHODS
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    /**
     * Get BTC-e Account information.
     * 
     * @return The account information
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public AccountInfo getAccountInfo() throws  BtceTechnicalException, BtceFunctionalException{
    	
    	JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.getInfo,null);
		AccountInfo accountInfo;
		try {
			accountInfo = _mapper.readValue(jsonObject.toString(), AccountInfo.class);
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}

    	return accountInfo;
    }
    
    /**
     * Get the BTC-e transaction history.
     * 
     * @param from The number of the transaction to start displaying with.
     * @param count The number of transactions for displaying.
     * @param from_id The ID of the transaction to start displaying with.
     * @param end_id The ID of the transaction to finish displaying with.
     * @param order Sorting.
     * @param since When to start the displaying.
     * @param end When to finish the displaying.
     * @param pair The pair to show the transactions.
     * @return The transaction history.    
     * 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Map<String,Order> getTransactionHistory(Long from, Long count, Long from_id, Long end_id, ListOrder order, Long since, Long end) throws BtceTechnicalException, BtceFunctionalException{
    	
    	Map<String, String> arguments = new HashMap<String, String>();
    	if (from != null){
    		arguments.put("from", String.valueOf(from));
    	}
    	if (count != null){
    		arguments.put("count", String.valueOf(count));
    	}
    	if (from_id != null){
    		arguments.put("from_id", String.valueOf(from_id));
    	}
    	if (end_id != null){
    		arguments.put("end_id", String.valueOf(end_id));
    	}
    	if (order != null){
    		arguments.put("order", order.name());
    	}
    	if (since != null){
    		arguments.put("since", String.valueOf(since));
    	}
    	if (end != null){
    		arguments.put("end", String.valueOf(end));
    	}
    	
    	// Call the active orders remote service
    	JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.TransHistory,arguments);
    	Map<String, Order> orders;
		try {
			orders = _mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,Order>>() { });
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
    	return orders;
    }
    
    /**
     * Get the BTC-e trade history.
     * 
     * @param from The number of the transaction to start displaying with.
     * @param count The number of transactions for displaying.
     * @param from_id The ID of the transaction to start displaying with.
     * @param end_id The ID of the transaction to finish displaying with.
     * @param order Sorting.
     * @param since When to start the displaying.
     * @param end When to finish the displaying.
     * @param pair The pair to show the transactions.
     * @return The trade history.
     * 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Map<String,Order> getTradeHistory(Long from, Long count, Long from_id, Long end_id, ListOrder order, Long since, Long end, Pair pair) throws BtceTechnicalException, BtceFunctionalException{
    	
    	Map<String, String> arguments = new HashMap<String, String>();
    	if (from != null){
    		arguments.put("from", String.valueOf(from));
    	}
    	if (count != null){
    		arguments.put("count", String.valueOf(count));
    	}
    	if (from_id != null){
    		arguments.put("from_id", String.valueOf(from_id));
    	}
    	if (end_id != null){
    		arguments.put("end_id", String.valueOf(end_id));
    	}
    	if (order != null){
    		arguments.put("order", order.name());
    	}
    	if (since != null){
    		arguments.put("since", String.valueOf(since));
    	}
    	if (end != null){
    		arguments.put("end", String.valueOf(end));
    	}
    	if (pair != null){
    		arguments.put("end", pair.name());
    	}
    	
    	// Call the active orders remote service
    	JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.TradeHistory,arguments);
    	Map<String, Order> orders;
		try {
			orders = _mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,Order>>() { });
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
    	return orders;
    }
    
    /**
     * Get the BTC-e active orders.
     * 
     * @return The active orders.
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Map<String,Order> getActiveOrders() throws BtceTechnicalException, BtceFunctionalException{
    	return getActiveOrders(null);
    }
    	
  
    /**
     * Get the BTC-e active orders for a specific pair.
     * 
     * @param pair The pair to display the orders.
     * @return The active orders.
     * 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Map<String,Order> getActiveOrders(Pair pair) throws BtceTechnicalException, BtceFunctionalException{
    	Map<String, String> arguments = new HashMap<String, String>();
    	if (pair!= null){
    		arguments.put("pair", pair.name());
    	}
    	
    	// Call the active orders remote service
    	Map<String,Order> orders = null;
    	
    	try{
    		JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.ActiveOrders,arguments);
    		try {
				orders = _mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,Order>>() { });
    		} catch (JsonParseException e) {
    			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
    		} catch (JsonMappingException e) {
    			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
    		} catch (IOException e) {
    			throw new BtceTechnicalException("BTC-e IO exeption", e);
    		}
    	} catch(BtceFunctionalException fe){
    		if (fe.getMessage().equals("no orders")){
    			return orders;
    		} else {
    			throw fe;
    		}
    	}
    	
    	return orders;
    }
    
    /**
     * Make a new trade on BTC-e.
     * 
     * @param pair The pair.
     * @param type The transaction type.
     * @param rate The rate to buy/sell.
     * @param amount The amount which is necessary to buy/sell.
     * @return The transaction result.
     * 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Transaction trade(Pair pair, TransactionType type, Double rate, Double amount) throws BtceTechnicalException, BtceFunctionalException{
    	
    	Map<String, String> arguments = new HashMap<String, String>();
    	arguments.put("pair", pair.name());
    	arguments.put("type", type.name());
    	arguments.put("rate", Double.toString(rate));
    	arguments.put("amount", Double.toString(amount));
    	
    	// Call the trade remote service
    	JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.Trade,arguments);
    	Transaction transaction;
		try {
			transaction = _mapper.readValue(jsonObject.toString(), Transaction.class);
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}

    	return transaction;
    }
    
    /**
     * Cancel an order on BTC-e.
     * 
     * @param orderId The order id to cancel.
     * @return The transaction cancellation result.
     * 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public Transaction cancelOrder(Long orderId) throws BtceTechnicalException, BtceFunctionalException{
 
    	Map<String, String> arguments = new HashMap<String, String>();
    	arguments.put("order_id",Long.toString(orderId));
    	
    	// Call the cancel order service
    	JSONObject jsonObject = authenticatedHTTPRequest(PrivateMethod.CancelOrder,arguments);
    	Transaction transaction;
		try {
			transaction = _mapper.readValue(jsonObject.toString(), Transaction.class);
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}

    	return transaction;
    }

    /**
     * Cancel all the active orders on BTC-e.
     * 
     * @return The list of the cancel orders.
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public List<Transaction> cancelAllOrders() throws BtceTechnicalException, BtceFunctionalException{
    	return cancelAllOrders(null);
    }
        
    	
    /**
     * Cancel all the active orders for a specific pair on BTC-e.
     * 
     * @param pair The pair to cancel the orders
     * @return The list of the cancel orders.
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     */
    public List<Transaction> cancelAllOrders(Pair pair) throws BtceTechnicalException, BtceFunctionalException{
    	 
    	Map<String,Order> orders = getActiveOrders(pair);
    	List<Transaction> transactions = new ArrayList<Transaction>();
    	
    	// We canceled all the active orders found
    	for (Iterator<Entry<String,Order>> iterator = orders.entrySet().iterator(); iterator.hasNext();) {
    		Entry<String,Order> entry = iterator.next();
    		transactions.add(cancelOrder(Long.parseLong(entry.getKey())));
		}
    	
    	return transactions;
    }
    
    
}
