package de.daniel.dengler.getRichBot;

import org.joda.time.DateTime;

import eu.verdelhan.ta4j.Tick;

/*
 * [{"date":1405699200,"high":0.0045388,"low":0.00403001,"open":0.00404545,"close":0.00435873,"volume":44.34555992,"quoteVolume":10311.88079097,"weightedAverage":0.00430043},{"date":1405713600,"high":0.00435,"low":0.00412,"open":0.00428012,"close":0.00412,"volume":19.12271662,"quoteVolume":4531.85801066,"weightedAverage":0.00421961},{"date":1405728000,"high":0.00435161,"low":0.00406,"open":0.00411473,"close":0.00435161,"volume":35.18169499,"quoteVolume":8430.50936646,"weightedAverage":0.00417313},
 */
public class ChartDataPoint {
	long date;
	double high;
	double low;
	double open;
	double close;
	double volume;
	
	public ChartDataPoint() {
		// TODO Auto-generated constructor stub
	}

	public ChartDataPoint setAll(long date, double high, double low, double open, double close, double volume) {
		this.date = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		return this;
	}

	public Tick toTick() {
		// TODO Auto-generated method stub
		return new Tick(new DateTime(date), open, high, low, close, volume);
	}

	public long getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}
	
	
}
