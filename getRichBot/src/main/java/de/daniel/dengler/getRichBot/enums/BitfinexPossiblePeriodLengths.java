package de.daniel.dengler.getRichBot.enums;

public enum BitfinexPossiblePeriodLengths {
	MIN_1(60, "1m"), MIN_5(300, "5m"), MIN_15(900, "15m"), MIN_30(1800,"30m"), HOUR_1(3600,"1h"), HOUR_3(
			3600 * 3, "3h"), HOUR_6(3600 * 6, "6h"), HOUR_12(3600 * 12,"12h"), DAY_1(3600 * 24,"1D"), WEEK_1(
			3600 * 24 * 7,"7D");
    //Available values: '1m', '5m', '15m', '30m', '1h', '3h', '6h', '12h', '1D', '7D', '14D', '1M'
	public int length;
	private String asString;

	BitfinexPossiblePeriodLengths(int length,String asString) {
		this.length = length;
		this.asString = asString;
	}

	public String asString() {
		// TODO Auto-generated method stub
		return asString;
	}
}
/**
 * 60. 300, 15m, 30m, 1h, 3h,6h,12h,1d,1w
 */
