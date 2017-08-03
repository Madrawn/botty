package de.daniel.dengler.getRichBot.enums;

public enum BitfinexPossiblePairs {
	BTCUSD, LTCUSD, LTCBTC, ETHUSD, ETHBTC, ETCUSD, ETCBTC, BFXUSD, BFXBTC, RRTUSD, RRTBTC, ZECUSD, ZECBTC, BCHBTC;

	public String getFront() {
		return this.toString().substring(0, 3);

	}

	public String getBack() {
		return this.toString().substring(3, 6);

	}

}
