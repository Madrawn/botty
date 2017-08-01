package de.daniel.dengler.getRichBot.exchangewrappers;

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
	double getCurrentBestAsk();
	double getCurrentBestBid();
}
