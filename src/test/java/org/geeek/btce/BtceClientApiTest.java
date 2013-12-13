/**
 * 
 */
package org.geeek.btce;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.Depth;
import org.geeek.btce.model.Ticker;
import org.geeek.btce.model.Trade;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the BTC-e client Trade API
 * 
 * @author Ludovic Toinel
 */
public class BtceClientApiTest {

	// the class instance to test
	private BtceClientApi btceClientApi = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		btceClientApi = new BtceClientApi();
	}


	/**
	 * Test method for {@link org.geeek.btce.BtceClientTradeApi#getTicker(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetTicker() throws BtceFunctionalException, BtceTechnicalException {
		
		// Get the ticker for the pair
		Ticker ticker = btceClientApi.getTicker(Pair.btc_eur);
		
		assertNotNull(ticker);
		assertTrue(ticker.getAvg() > 0);
		assertTrue(ticker.getBuy() > 0);
		assertTrue(ticker.getHigh() > 0);
		assertTrue(ticker.getLast() > 0);
		assertTrue(ticker.getLow() > 0);
		assertTrue(ticker.getSell()> 0);
		assertTrue(ticker.getUpdated() > 0);
		assertTrue(ticker.getVol() > 0);
		assertTrue(ticker.getVol_cur() > 0);
	}
	

	/**
	 * Test method for {@link org.geeek.btce.BtceClientTradeApi#getTrades(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetTrades() throws BtceFunctionalException, BtceTechnicalException {
		
		// Get the trades for the pair
		List<Trade> trades = btceClientApi.getTrades(Pair.btc_eur);
		
		assertNotNull(trades);
		assertTrue(trades.get(0).getAmount() > 0);
		assertTrue(trades.get(0).getPrice() > 0);
		assertTrue(trades.get(0).getTid() > 0);
		assertTrue(trades.get(0).getTimestamp() > 0);
		assertNotNull(trades.get(0).getType());
	}

	
	/**
	 * Test method for {@link org.geeek.btce.BtceClientTradeApi#getDepth(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetDepth() throws BtceFunctionalException, BtceTechnicalException {
		
		// Get the trades for the pair
		Depth depth = btceClientApi.getDepth(Pair.btc_eur);
		
		assertNotNull(depth);
		assertTrue(depth.getAsks().get(0)[0] > 0);
		assertTrue(depth.getAsks().get(0)[1] > 0);
		assertTrue(depth.getBids().get(0)[0] > 0);
		assertTrue(depth.getBids().get(0)[1] > 0);
	}
}
