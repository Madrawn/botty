package de.daniel.dengler.getRichBot.exchangewrappers;

import java.io.IOException;
import java.net.MalformedURLException;

import de.daniel.dengler.getRichBot.ChartDataPoint;

public interface IExchangeWrapper {
	
	
	ChartDataPoint[] getChart();
	void placeMarketBuyOrder(long amount);
	void placeMarketSellOrder(long amount);
	double getBalance(String currency);
	ChartDataPoint getMostRecentCandle();

	void setSettings(IExchangeWrapperSettings settings);
	IExchangeWrapperSettings getSettings();
	double getDailyLow();
	double getDailyHigh();
	double getCurrentBestAsk() throws MalformedURLException, IOException;
	double getCurrentBestBid() throws MalformedURLException, IOException;
}
