package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;

public class BitfinexWrapperConnectorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrderBook() {
		BitfinexOrderBook result = new BitfinexWrapperConnector().getOrderBook("btcusd");
		assertNotNull(result);
		
	}
	
	@Test
	public void candlesTest() {
		
		String sResult = new BitfinexWrapperConnector().candles("1m", BitfinexPossiblePairs.LTCBTC.toString(), "last");
		System.out.println(sResult);
		assertTrue(sResult != null);
	}

}
