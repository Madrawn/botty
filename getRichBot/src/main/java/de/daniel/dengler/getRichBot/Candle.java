package de.daniel.dengler.getRichBot;

import java.util.Date;

public class Candle {
	double high, low, open, close, period; 
	long startDate;
	
	public Candle(){
		this(5d,5d,5d,5d,5d,new Date().getTime());
	}

	public Candle(double high, double low, double open, double close, double period, long time) {
		setHigh(high);
		setLow(low);
		setOpen(open);
		setClose(close);
		setPeriod(period);
		setStartDate(time);
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}

	public double getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

}
