package org.geeek.btce;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.enums.PublicMethod;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.Depth;
import org.geeek.btce.model.Ticker;
import org.geeek.btce.model.Trade;
import org.geeek.btce.model.PairInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A Java Client for BTC-e V3 APIs.
 * 
 * @see "https://btc-e.com/api/3/documentation#info"
 * @author Ludovic Toinel
 * @version 1.0.0
 */
public class BtceClientApi {

	// Logger
	private final static Logger LOGGER = LoggerFactory.getLogger(BtceClientApi.class);
	
	// Server URI
	private final static String API_URI = "https://btc-e.com/api/3/";
	
	// JSON Object Mapper
	private ObjectMapper _mapper;

	/**
	 * Default constructor
	 * 
	 * @param key The API key.
	 * @param secret The secret key.
	 */
	public BtceClientApi(){
		this._mapper = new ObjectMapper();
	}

	
	/**
     * Execute an HTTP query on the BTC-e public API.
     * 
	 * @param method
	 * @param pair
	 * @return
	 * @throws BtceFunctionalException
	 * @throws BtceTechnicalException
	 */
	private final JSONObject publicHTTPRequest(PublicMethod method, String arg) throws BtceFunctionalException, BtceTechnicalException{
		try{
			String uri = API_URI + method.name();
			
			if (arg != null){
				 uri = uri + "/" + arg;
			}
			
			LOGGER.debug("Public HTTP Request : " + uri);
			
			Response response = Request.Get(uri).execute();
			
			Content content = response.returnContent();
			
			if( content != null) {  
	
				LOGGER.debug("Received message : {}", content.asString());
				
				// Convert the HTTP request return value to JSON to parse further.
				JSONObject jsonResult = JSONObject.fromObject(content.asString());
	
				// Check, if the request was successful
	
				if( jsonResult.has("success") && jsonResult.getInt("success") == 0) {  

					// The request failed.
					String errorMessage = jsonResult.getString("error");
					
					LOGGER.error("BTC-e API request failed : {}", errorMessage);
					throw new BtceFunctionalException(errorMessage);
					
				} else {
					
					// Request succeeded!
				    return jsonResult;
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
	
    
    ///////////////////////////////////////////////////////////////////////////////////
    // ACTION METHODS
    ///////////////////////////////////////////////////////////////////////////////////
 
	
    /**
     * This method provides all the information about bidding on a pair, such as: the highest price, lowest price, 
     * average price, trading volume, trading volume in the currency of the last deal, the price of buying and selling. 
     * All information is provided in the last 24 hours.
     * 
     * @param pair The pair.
     * @return The current ticker for this pair.
     * 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public Ticker getTicker(Pair pair) throws BtceFunctionalException, BtceTechnicalException{
    
    	JSONObject jsonObject = publicHTTPRequest(PublicMethod.ticker, pair.name());
    	Ticker ticker;
		try {
			ticker = _mapper.readValue(jsonObject.getString(pair.name()), Ticker.class);
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
    	return ticker;
    }
    
    
    /**
     * This method provides information on the latest deals.
     * Optional further receives GET-parameter limit , which indicates how many transactions need to display (150 by default).
     * Takes a value less than 2000.
     * 
     * @param pair The pair.
     * @return The trades for this pair.
     * 
     * @throws BtceTechnicalException 
     * @throws BtceFunctionalException 
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public List<Trade> getTrades(Pair pair) throws BtceFunctionalException, BtceTechnicalException{
    
    	JSONObject jsonObject = publicHTTPRequest(PublicMethod.trades, pair.name());
    	Map<String, List<Trade>> trades;
		try {
			trades = _mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,List<Trade>>>() { });
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
 
    	return trades.get(pair.name());
    }
   
    /**
     * This method provides information on active warrants pair.
     * Additionally takes an optional parameter GET- limit , which indicates how many orders you want to display (default 150).
     * Takes a value less than 2000.
     * 
     * @param pair The pair.
     * @return The depths for this pair.
     * 
     * @throws BtceFunctionalException
     * @throws BtceTechnicalException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public Depth getDepth(Pair pair) throws BtceFunctionalException, BtceTechnicalException{
        
    	JSONObject jsonObject = publicHTTPRequest(PublicMethod.depth, pair.name());
    	Map<String, Depth> depth;
		try {
			depth = _mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,Depth>>() { });
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
 
    	return depth.get(pair.name());
    }
    
    /**
     * This method provides all the information about currently active pairs, such as the maximum number of digits 
     * after the decimal point in the auction, the minimum price, maximum price, minimum quantity purchase / sale, 
     * hidden whether the pair and the pair commission.
     * 
     * @return the active pair information
     * @throws BtceFunctionalException
     * @throws BtceTechnicalException
     */
    public Map<String,PairInfo> getInfo() throws BtceFunctionalException, BtceTechnicalException{
        
    	JSONObject jsonObject = publicHTTPRequest(PublicMethod.info, null);
    	Map<String,PairInfo> pairInfo;
		try {
			pairInfo = _mapper.readValue(jsonObject.getJSONObject("pairs").toString(), new TypeReference<Map<String,PairInfo>>() { });
		} catch (JsonParseException e) {
			throw new BtceTechnicalException("BTC-e Json parse exeption", e);
		} catch (JsonMappingException e) {
			throw new BtceTechnicalException("BTC-e Json mapping exeption", e);
		} catch (IOException e) {
			throw new BtceTechnicalException("BTC-e IO exeption", e);
		}
    	
 
    	return pairInfo;
    }
   
    
}
