package de.daniel.dengler.getRichBot;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;

import de.daniel.dengler.getRichBot.BackTradingFrame.Strategies;
import de.daniel.dengler.getRichBot.exchangewrappers.PoloWrapper;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.DifferenceIndicator;
import eu.verdelhan.ta4j.trading.rules.NotRule;

public class DataOverlord {

	public static ChartDataPoint[] chartPoints;
	public static String currencyPair;
	public static int period;
	public static long start;
	public static long end;
	public static int numPeriods;
	public static TimeSeries series;
	public static String res2;
	public static String res1;
	public static Strategy strat;
	public static boolean live = false;
	public static String minGain;
	static String chickenOut;

	public static boolean collectChart(MainWindow me) {
		end = new Date().getTime() / 1000;
		period = me.getPeriod();
		numPeriods = me.getNumPeriods();
		start = end - (numPeriods * period);

		currencyPair = me.getCurrencyPair();
		try {
			chartPoints = PoloWrapper.returnChartData(currencyPair, period,
					start, end);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (chartPoints == null)
			return false;

		series = new TimeSeries(Period.minutes(5));
		series.setMaximumTickCount(numPeriods);

		for (int i = 0; i < chartPoints.length; i++) {
			series.addTick(new Tick(new DateTime(chartPoints[i].date * 1000),
					chartPoints[i].open, chartPoints[i].high,
					chartPoints[i].low, chartPoints[i].close,
					chartPoints[i].volume));
		}

		return chartPoints.length > 1;
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildRSIStrategy(TimeSeries)} instead
	 */
	public static Strategy buildRSIStrategy(TimeSeries series) {
		return StrategyBuilder.buildRSIStrategy(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildCCIStrategy(TimeSeries)} instead
	 */
	public static Strategy buildCCIStrategy(TimeSeries series) {
		return StrategyBuilder.buildCCIStrategy(series);
	}

	/**
	 * @param series
	 *            a time series
	 * @return a moving momentum strategy
	 * @deprecated Use {@link StrategyBuilder#buildMovingMomentumStrategy(TimeSeries)} instead
	 */
	public static Strategy buildMovingMomentumStrategy(TimeSeries series) {
		return StrategyBuilder.buildMovingMomentumStrategy(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildNilsStrategy(TimeSeries)} instead
	 */
	public static Strategy buildNilsStrategy(TimeSeries series) {
		return StrategyBuilder.buildNilsStrategy(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildFishStrategy(TimeSeries)} instead
	 */
	public static Strategy buildFishStrategy(TimeSeries series) {
		return StrategyBuilder.buildFishStrategy(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildDanielStrategy(TimeSeries)} instead
	 */
	public static Strategy buildDanielStrategy(TimeSeries series) {
		return StrategyBuilder.buildDanielStrategy(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildEMAStrategiesMap(TimeSeries)} instead
	 */
	public static Map<Strategy, String> buildEMAStrategiesMap(TimeSeries series) {
		return StrategyBuilder.buildEMAStrategiesMap(series);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#buildLongFishStrategiesMap(TimeSeries)} instead
	 */
	public static Map<Strategy, String> buildFishStrategiesMap(TimeSeries series) {
		return StrategyBuilder.buildLongFishStrategiesMap(series);
	}

	/**
	 * @deprecated Use {@link TradingBot#testStrategy(Strategies)} instead
	 */
	public static void testStrategy(Strategies strt) {
		TradingBot.testStrategy(strt);
	}

	/**
	 * @deprecated Use {@link StrategyBuilder#findBest(TimeSeries,Strategies)} instead
	 */
	private static Strategy findBest(TimeSeries series2, Strategies strt) {
		return StrategyBuilder.findBest(series2, strt);
	}

	public static void updateSeries() {
		long end2 = new Date().getTime() / 1000;
		Tick fakeLastTick = series.getLastTick();
		ChartDataPoint[] chartUpdate;
		try {
			chartUpdate = PoloWrapper.returnChartData(currencyPair, period,
					end2 - (period * 5), end2);
		} catch (IOException e1) {
			e1.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("We're maybe fucked but retry anyway!");
			updateSeries();
			return;
		}
		ChartDataPoint lastTick = chartUpdate[chartUpdate.length - 1];
		Tick tick = new Tick(new DateTime(lastTick.date * 1000), lastTick.open,
				lastTick.high, lastTick.low, lastTick.close, lastTick.volume);

		if (fakeLastTick.getEndTime().getMillis() >= tick.getEndTime()
				.getMillis()) {
			System.out.println("Updated Tick was same as last Tick. Retrying");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			updateSeries();
		} else {

			System.out.println("OldTickTime: " + fakeLastTick.getEndTime()
					+ " NewTickTime: " + tick.getEndTime());
			System.out.println("Triple Check OldTickTime: "
					+ series.getLastTick().getDateName() + "|"
					+ series.getLastTick().getEndTime().getMillis());

			series.addTick(tick);
		}
	}

	enum State {
		buy, sell, wait
	}

	public static State checkLiveStrategy() {

		int endIndex = series.getEnd();
		if (strat.shouldEnter(endIndex)) {
			// Entering...
			return State.buy;

		} else if (strat.shouldExit(endIndex)) {
			// Exiting...
			return State.sell;
		}

		return State.wait;

	}

}
