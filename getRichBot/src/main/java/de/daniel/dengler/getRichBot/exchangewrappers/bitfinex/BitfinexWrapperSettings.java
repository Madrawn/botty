package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import java.util.Arrays;
import java.util.TreeSet;

import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePeriodLengths;
import de.daniel.dengler.getRichBot.enums.PossibleExchanges;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;

public class BitfinexWrapperSettings implements IExchangeWrapperSettings {

	private int periodLength;

	BitfinexPossiblePairs pair;
	BitfinexPossiblePeriodLengths pLength;

	private int numPeriods;

	private boolean isUpdating;
	
	public BitfinexWrapperSettings(BitfinexPossiblePairs pair, BitfinexPossiblePeriodLengths pLength, int numPeriods) {
		this.pair = pair;
		this.pLength = pLength;
		this.numPeriods =numPeriods;
	}

	public String getExchangeName() {
		return "Bitfinex";
	}

	/**
	 * 60. 300, 15m, 30m, 1h, 3h,6h,12h,1d,1w
	 */


	/**
	 * Supported Pairs BTCUSD, LTCUSD, LTCBTC, ETHUSD, ETHBTC, ETCUSD, ETCBTC,
	 * BFXUSD, BFXBTC, RRTUSD, RRTBTC, ZECUSD, ZECBTC
	 */



	public int getPeriodLength() {
		return this.pLength.length;
	}

	public BitfinexPossiblePairs getCurrencyPair() {
		return this.pair;
	}



	public boolean arePrarametersFine() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValidPeriodLength(int periodLength) {
		// TODO Auto-generated method stub
		return false;
	}

	public <E2 extends Enum<E2>> IExchangeWrapperSettings setPeriodLength(
			E2 periodLength) {
		
		pLength = (BitfinexPossiblePeriodLengths) periodLength;
		return this;
	}

	public <E extends Enum<E>> IExchangeWrapperSettings setCurrencyPair(
			E currencyPair) {
		pair = (BitfinexPossiblePairs) currencyPair;
		return this;
	}

	public IExchangeWrapperSettings setNumPeriods(int num) {
		this.numPeriods = num;
		return this;
	}

	public PossibleExchanges getExchange() {
		return PossibleExchanges.BITFINEX;
	}

	public boolean isUpdating() {
		return this.isUpdating;
	}

	public IExchangeWrapperSettings setIsUpdating(boolean b) {
		this.isUpdating = b;
		return this;
	}

	public int getNumPeriods() {
		// TODO Auto-generated method stub
		return this.numPeriods;
	}

	public String getPeriodLengthAsString() {
		// Available values: '1m', '5m', '15m', '30m', '1h', '3h', '6h', '12h', '1D', '7D', '14D', '1M'
		return pLength.asString();
	}

	public String getPairAsString() {
		return pair.toString();
	}

}
