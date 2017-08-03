package de.daniel.dengler.getRichBot.exchangewrappers;

import java.io.IOException;
import java.net.MalformedURLException;

import de.daniel.dengler.getRichBot.ChartDataPoint;
import eu.verdelhan.ta4j.TimeSeries;

public class ExchangeWrapper implements Runnable, IExchangeWrapper {

	enum OrderTypes {
		BUY, SELL;
	}

	protected IExchangeWrapperSettings settings;
	protected IExchangeConnector connector;
	protected TimeSeries currentSeries;

	public ExchangeWrapper(IExchangeWrapperSettings settings,
			IExchangeConnector connector) {
		this.settings = settings;
		this.connector = connector;

	}

	public ChartDataPoint[] requestChart() throws Exception {
		if (settings.arePrarametersFine()) {
			return getChart();
		}

		throw new Exception("Parameters not set, cannot request chart");

	}

	public ChartDataPoint[] getChart() {
		try {
			return connector.getChart(settings);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getSeriesEndIndex() {
		// TODO Auto-generated method stub
		return currentSeries.getEnd();
	}

	public void run() {
		// TODO Auto-generated method stub
		if (currentSeries == null)
			;

	}

	public void placeMarketBuyOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	public void placeMarketSellOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	public double getBalance(String currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ChartDataPoint getMostRecentCandle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSettings(IExchangeWrapperSettings settings) {
		// TODO Auto-generated method stub

	}

	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getDailyLow() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDailyHigh() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCurrentBestAsk() throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCurrentBestBid() throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
