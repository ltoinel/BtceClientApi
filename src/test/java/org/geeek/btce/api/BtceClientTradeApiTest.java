/**
 * 
 */
package org.geeek.btce.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.geeek.btce.api.BtceClientTradeApi;
import org.geeek.btce.exception.BtceFunctionalException;
import org.geeek.btce.exception.BtceTechnicalException;
import org.geeek.btce.model.AccountInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the BTC-e client API.
 * 
 * @author Ludovic Toinel
 */
public class BtceClientTradeApiTest {

	// the class instance to test
	private BtceClientTradeApi btceClientTradeApi = null;
	
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
		btceClientTradeApi = new BtceClientTradeApi(key, secret);
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#getAccountInfo()}.
	 * 
	 * @throws IOException 
	 * @throws BtceFunctionalException 
	 * @throws BtceTechnicalException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void testGetAccountInfo() throws BtceTechnicalException, BtceFunctionalException {
		
		// Call the service
		AccountInfo accountInfo = btceClientTradeApi.getAccountInfo();
		
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
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#getTransactionHistory(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, org.geeek.btce.enums.ListOrder, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public void testGetTransactionHistory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#getTradeHistory(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, org.geeek.btce.enums.ListOrder, java.lang.Long, java.lang.Long, org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testGetTradeHistory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#getActiveOrders()}.
	 */
	@Test
	public void testGetActiveOrders() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#getActiveOrders(org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testGetActiveOrdersPair() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#trade(org.geeek.btce.enums.Pair, org.geeek.btce.enums.TransactionType, java.lang.Double, java.lang.Double)}.
	 */
	@Test
	public void testTrade() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#cancelOrder(java.lang.Long)}.
	 */
	@Test
	public void testCancelOrder() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#cancelAllOrders()}.
	 */
	@Test
	public void testCancelAllOrders() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.geeek.btce.api.BtceClientTradeApi#cancelAllOrders(org.geeek.btce.enums.Pair)}.
	 */
	@Test
	public void testCancelAllOrdersPair() {
		fail("Not yet implemented");
	}

}
