package de.daniel.dengler.getRichBot;

import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;

import de.daniel.dengler.getRichBot.ChartDataPoint;
import de.daniel.dengler.getRichBot.enums.PossibleExchanges;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;
import eu.verdelhan.ta4j.Tick;

public class TestWrapper implements IExchangeWrapper {

	private IExchangeWrapperSettings sets;
	private ChartDataPoint[] cd;

	public TestWrapper() {
		this.setSettings(new IExchangeWrapperSettings() {

			@Override
			public <E extends Enum<E>> IExchangeWrapperSettings setPeriodLength(
					E periodLength) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IExchangeWrapperSettings setNumPeriods(int num) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IExchangeWrapperSettings setIsUpdating(boolean b) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <E extends Enum<E>> IExchangeWrapperSettings setCurrencyPair(
					E currencyPair) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isValidPeriodLength(int periodLength) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isUpdating() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getPeriodLength() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getExchangeName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PossibleExchanges getExchange() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <E extends Enum<E>> E getCurrencyPair() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean arePrarametersFine() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getPeriodLengthAsString() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPairAsString() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	@Override
	public ChartDataPoint[] getChart() {

		//create fake chart
		int numTicks = new Random().nextInt(10) +1 ;
		ChartDataPoint[] cd = new ChartDataPoint[numTicks];
		for (int i = 0; i < cd.length; i++) {
			cd[i] = new ChartDataPoint().setAll(i, new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000));
		
		}
		
		this.cd = cd;
		return cd;
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
	public double getBalance(String currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChartDataPoint getMostRecentCandle() {
		// TODO Auto-generated method stub
		return new ChartDataPoint().setAll(new Date().getTime(), new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000));
	}

	@Override
	public void setSettings(IExchangeWrapperSettings settings) {
		this.sets = settings;
	}

	@Override
	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return this.sets;
	}

	@Override
	public double getDailyLow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDailyHigh() {
		// TODO Auto-generated method stub
		return 0;
	}

}
