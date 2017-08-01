package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import static org.junit.Assert.*;

import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePeriodLengths;

public class BitfinexWrapperSettingsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void setAllTheShutTest() {
		BitfinexWrapperSettings b = new BitfinexWrapperSettings();
		
		BitfinexPossiblePairs currencyPair = BitfinexPossiblePairs.BTCUSD; 
		b.setCurrencyPair(currencyPair);
		BitfinexPossiblePairs newPair = b.getCurrencyPair();
		assertTrue(currencyPair.equals(newPair));
		
		boolean bool = true;
		b.setIsUpdating(bool);
		boolean newBool = b.isUpdating();
		assertTrue(bool == newBool);
		
		int num = 50;
		b.setNumPeriods(num);
		int newNum = b.getNumPeriods();
		assertTrue(num == newNum);
		
		BitfinexPossiblePeriodLengths newPeriodLength, periodLength = BitfinexPossiblePeriodLengths.HOUR_1;
		b.setPeriodLength(periodLength);
		int pl = b.getPeriodLength();
		assertTrue(pl == 3600);
		
		
	}

}
