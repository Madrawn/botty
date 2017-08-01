package de.daniel.dengler.getRichBot.exchangewrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import de.daniel.dengler.getRichBot.ApiKeySecretPair;
import de.daniel.dengler.getRichBot.ChartDataPoint;

public class PoloWrapper extends AExchangeWrapper{
	public static final String publicApiUrl = "https://poloniex.com/public";
	
	//(String currencyPair, 
	//int period,/* valid values are 300, 900, 1800, 7200, 14400, and 86400*/long start, long end)
	
	public static ChartDataPoint[] getChart() throws IOException{
				//https://poloniex.com/public?command=returnChartData&currencyPair=BTC_XMR&start=1405699200&end=9999999999&period=14400
		String query = String.format("?command=returnChartData&currencyPair=%s&start=%s&end=%s&period=%s", currencyPair,start, end, period);
		ChartDataPoint[] chart = null;
		try {
			URL url = new URL(publicApiUrl + query);
				InputStream i = url.openStream();
				
				BufferedReader buf = new BufferedReader(new InputStreamReader(i));
				String result = null,line;
				
				while((line = buf.readLine()) != null){
					result =  line;
					
				}
				if(result.equals("{\"error\":\"Invalid currency pair.\"}")){
					
				} else {
					
					Gson g = new Gson();
					chart = g.fromJson(result, ChartDataPoint[].class);
				}
				
			
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		return chart;
		
	}

	@Override
	public ChartDataPoint[] getChart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeMarketBuyOrder(long ammount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeMarketSellOrder(long ammount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getBalance(String currency, ApiKeySecretPair key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChartDataPoint getMostRecentCandle() {
		// TODO Auto-generated method stub
		return null;
	}
}
