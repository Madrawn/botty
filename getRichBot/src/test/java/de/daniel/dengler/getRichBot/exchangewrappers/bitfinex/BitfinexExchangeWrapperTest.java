package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.daniel.dengler.getRichBot.ChartDataPoint;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePeriodLengths;

public class BitfinexExchangeWrapperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	
	
	@Test
	public void testMostRecentCandle() {
		BitfinexExchangeWrapper wr = new BitfinexExchangeWrapper((BitfinexWrapperSettings) new BitfinexWrapperSettings().setPeriodLength(BitfinexPossiblePeriodLengths.HOUR_1).setCurrencyPair(BitfinexPossiblePairs.BTCUSD), new BitfinexWrapperConnector());
		ChartDataPoint cd = wr.getMostRecentCandle();
		assertNotNull(cd);
		assertNotNull(cd.toTick());
		assertNotNull(cd.toTick().getOpenPrice());
		System.out.println(cd.toTick().toString());
		
	}

}
