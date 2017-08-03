package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import java.io.IOException;
import java.net.MalformedURLException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.daniel.dengler.getRichBot.ChartDataPoint;
import de.daniel.dengler.getRichBot.UI.AllmightyController;
import de.daniel.dengler.getRichBot.exchangewrappers.ExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;

public class BitfinexExchangeWrapper extends ExchangeWrapper implements
		IExchangeWrapper {

	BitfinexWrapperSettings mySettings = (BitfinexWrapperSettings) this
			.settings;
	BitfinexWrapperConnector myConnector = (BitfinexWrapperConnector) this
			.connector;

	public BitfinexExchangeWrapper(BitfinexWrapperSettings settings,
			BitfinexWrapperConnector conn) {
		super(settings, conn);
	}

	@Override
	public ChartDataPoint[] getChart() {
		
		String timeFrame, symbol;
		timeFrame = mySettings.getPeriodLengthAsString();
		symbol = mySettings.getPairAsString();
		int num = mySettings.getNumPeriods();
		String response = myConnector.candles(timeFrame, symbol, "hist", num);		
		ChartDataPoint[] chartData = new ChartDataPoint[num];
		Gson g = new Gson();
		JsonArray responseArray = new JsonParser().parse(response).getAsJsonArray();
		if (responseArray.size() != chartData.length) {
			System.out.println("Not the same length");
			return null;
			
		} else {
			
			for (int i = 0; i < chartData.length; i++) {
				JsonArray singleJsonResponse = (JsonArray) responseArray.get(i);
				long date = g.fromJson(singleJsonResponse.get(0), long.class);
				double high = g.fromJson(singleJsonResponse.get(1), double.class);
				double low = g.fromJson(singleJsonResponse.get(2), double.class);
				double open = g.fromJson(singleJsonResponse.get(3), double.class);
				double close = g.fromJson(singleJsonResponse.get(4), double.class);
				double volume = g.fromJson(singleJsonResponse.get(5), double.class);
				chartData[i] = new ChartDataPoint().setAll(date, high, low, open, close, volume);
			}
			return chartData;
		}
	}

	@Override
	public void placeMarketBuyOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeMarketSellOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	public double getBalance(String currency) {
		return myConnector.getBalance(currency, AllmightyController.getKey());
	}

	@Override
	public ChartDataPoint getMostRecentCandle() {
		String timeFrame, symbol;
		timeFrame = mySettings.getPeriodLengthAsString();
		symbol = mySettings.getPairAsString();
		String response = myConnector.candles(timeFrame, symbol, "last");
		JsonParser jp = new JsonParser();
		JsonArray a = jp.parse(response).getAsJsonArray();
		Gson g = new Gson();
		
		long date = g.fromJson(a.get(0), long.class);
		double high = g.fromJson(a.get(1), double.class);
		double low = g.fromJson(a.get(2), double.class);
		double open = g.fromJson(a.get(3), double.class);
		double close = g.fromJson(a.get(4), double.class);
		double volume = g.fromJson(a.get(5), double.class);
		ChartDataPoint cd = new ChartDataPoint().setAll(date, high, low, open, close, volume);
		return cd;
	}

	@Override
	public void setSettings(IExchangeWrapperSettings settings) {
		// TODO Auto-generated method stub

	}

	@Override
	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return this.settings;
	}
	
	
	public double getCurrentBestAsk() throws MalformedURLException, IOException {
		BitfinexOrderBook orderBook = myConnector.getOrderBook(settings.getPairAsString());
		return orderBook.asks[0].price;
	}
	public double getCurrentBestBid() throws MalformedURLException, IOException {
		BitfinexOrderBook orderBook = myConnector.getOrderBook(settings.getPairAsString());
		return orderBook.bids[0].price;
	}


}
