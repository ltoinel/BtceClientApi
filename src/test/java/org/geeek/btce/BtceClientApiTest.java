/**
 * 
 */
package org.geeek.btce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.geeek.btce.enums.Pair;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.AccountInfo;
import org.geeek.btce.model.Depth;
import org.geeek.btce.model.Ticker;
import org.geeek.btce.model.Trade;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the BTC-e client API
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
		Properties properties = new Properties();
		InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
		properties.load(is);
		String key = properties.getProperty("key");
		String secret = properties.getProperty("secret");
		btceClientApi = new BtceClientApi(key, secret);
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getAccountInfo()}.
	 * 
	 * @throws IOException 
	 * @throws BtceFunctionalException 
	 * @throws BtceTechnicalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetAccountInfo() throws JsonParseException, JsonMappingException, BtceTechnicalException, BtceFunctionalException, IOException {
		
		// Call the service
		AccountInfo accountInfo = btceClientApi.getAccountInfo();
		
		// Check funds
		assertNotNull(accountInfo.getFunds());
		
		// Check rights
		assertNotNull(accountInfo.getRights());
		assertTrue(accountInfo.getRights().isInfo());
		assertTrue(accountInfo.getRights().isTrade());
		assertFalse(accountInfo.getRights().isWithdraw());
		
		// Check transactions
		assertTrue(accountInfo.getTransaction_count() > 0);
		assertEquals(0,accountInfo.getOpen_orders());
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getTransactionHistory(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, org.geeek.btce.enums.ListOrder, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public void testGetTransactionHistory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getTradeHistory(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, org.geeek.btce.enums.ListOrder, java.lang.Long, java.lang.Long, org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testGetTradeHistory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getActiveOrders()}.
	 */
	@Test
	public void testGetActiveOrders() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getActiveOrders(org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testGetActiveOrdersPair() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#trade(org.geeek.btce.enums.Pair, org.geeek.btce.enums.TransactionType, java.lang.Double, java.lang.Double)}.
	 */
	@Test
	public void testTrade() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#cancelOrder(java.lang.Long)}.
	 */
	@Test
	public void testCancelOrder() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#cancelAllOrders()}.
	 */
	@Test
	public void testCancelAllOrders() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#cancelAllOrders(org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testCancelAllOrdersPair() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.BtceClientApi#getTicker(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetTicker() throws JsonParseException, JsonMappingException, BtceFunctionalException, BtceTechnicalException, IOException {
		
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
	 * Test method for {@link org.geeek.btce.BtceClientApi#getTrades(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetTrades() throws JsonParseException, JsonMappingException, BtceFunctionalException, BtceTechnicalException, IOException {
		
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
	 * Test method for {@link org.geeek.btce.BtceClientApi#getDepth(org.geeek.btce.enums.Pair)}.
	 * 
	 * @throws IOException 
	 * @throws BtceTechnicalException 
	 * @throws BtceFunctionalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetDepth() throws JsonParseException, JsonMappingException, BtceFunctionalException, BtceTechnicalException, IOException {
		
		// Get the trades for the pair
		Depth depth = btceClientApi.getDepth(Pair.btc_eur);
		
		assertNotNull(depth);
		assertTrue(depth.getAsks().get(0)[0] > 0);
		assertTrue(depth.getAsks().get(0)[1] > 0);
		assertTrue(depth.getBids().get(0)[0] > 0);
		assertTrue(depth.getBids().get(0)[1] > 0);
	}
}
