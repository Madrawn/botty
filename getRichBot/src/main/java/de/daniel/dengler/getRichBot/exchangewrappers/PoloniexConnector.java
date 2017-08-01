package de.daniel.dengler.getRichBot.exchangewrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import de.daniel.dengler.getRichBot.ChartDataPoint;

public class PoloniexConnector implements IExchangeConnector {

	private String publicApiUrl = "https://poloniex.com/public";

	@Override
	public void placeBuyOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeSellOrder(long ammount) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getBalance(String currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChartDataPoint getMostRecentCandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSettings(IExchangeWrapperSettings settings) {
		// TODO Auto-generated method stub

	}

	@Override
	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartDataPoint[] getChart(IExchangeWrapperSettings settings)
			throws IOException, MalformedURLException {
		// https://poloniex.com/public?command=returnChartData&currencyPair=BTC_XMR&start=1405699200&end=9999999999&period=14400
		String query = String
				.format("?command=returnChartData&currencyPair=%s&start=%s&end=%s&period=%s",
						settings.getCurrencyPair(), settings.getStart(),
						settings.getEnd(), settings.getPeriodLength());
		ChartDataPoint[] chart = null;

		URL url = new URL(publicApiUrl + query);
		InputStream i = url.openStream();

		BufferedReader buf = new BufferedReader(new InputStreamReader(i));
		String result = null, line;

		while ((line = buf.readLine()) != null) {
			result = line;

		}
		if (result.equals("{\"error\":\"Invalid currency pair.\"}")) {

		} else {

			Gson g = new Gson();
			chart = g.fromJson(result, ChartDataPoint[].class);
		}

		return chart;
	}

}
