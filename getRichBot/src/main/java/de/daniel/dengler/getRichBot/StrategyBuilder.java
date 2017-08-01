package de.daniel.dengler.getRichBot;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.daniel.dengler.getRichBot.BackTradingFrame.Strategies;
import de.daniel.dengler.getRichBot.customindicators.DelayIndicator;
import de.daniel.dengler.getRichBot.enums.StrategyType;
import eu.verdelhan.ta4j.AnalysisCriterion;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.analysis.criteria.TotalProfitCriterion;
import eu.verdelhan.ta4j.indicators.oscillators.CCIIndicator;
import eu.verdelhan.ta4j.indicators.oscillators.FisherIndicator;
import eu.verdelhan.ta4j.indicators.oscillators.StochasticOscillatorKIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.simple.MedianPriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.EMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.MACDIndicator;
import eu.verdelhan.ta4j.indicators.trackers.RSIIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.StopGainRule;
import eu.verdelhan.ta4j.trading.rules.StopLossRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;

public class StrategyBuilder {

	public Strategy buildStrategy(StrategyType type, TimeSeries series,
			String... args) {
		Strategy newStrat = null;

		switch (type) {
		case EMA:

			break;
		case FISHER:
			newStrat = StrategyBuilder.buildFishStrategy(series,
					Integer.parseInt(args[0]), Integer.parseInt(args[1]), true);
			break;
		case AUTO_EMA:
			newStrat = StrategyBuilder.findBestFromMap(series,
					StrategyBuilder.buildEMAStrategiesMap(series));

			break;
		case AUTO_FISHER:
			newStrat = StrategyBuilder.findBestFromMap(series,
					StrategyBuilder.buildLongFishStrategiesMap(series));
			break;

		default:
			break;
		}

		return newStrat;

	}

	public static Strategy[] longShortFisher(TimeSeries series, int i, int j) {

		Strategy[] result = new Strategy[2];
		result[0] = buildFishStrategy(series, i, j, true);
		result[1] = buildFishStrategy(series, i, j, false);

		return result;
	}

	public static Strategy buildRSIStrategy(TimeSeries series) {
		if (series == null) {
			throw new IllegalArgumentException("Series cannot be null");
		}

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
		SMAIndicator longSma = new SMAIndicator(closePrice, 200);

		// We use a 2-period RSI indicator to identify buying
		// or selling opportunities within the bigger trend.
		RSIIndicator rsi = new RSIIndicator(closePrice, 2);

		// Entry rule
		// The long-term trend is up when a security is above its 200-period
		// SMA.
		Rule entryRule = new OverIndicatorRule(shortSma, longSma) // Trend
				.and(new CrossedDownIndicatorRule(rsi, Decimal.valueOf(5))) // Signal
																			// 1
				.and(new OverIndicatorRule(shortSma, closePrice)); // Signal 2

		// Exit rule
		// The long-term trend is down when a security is below its 200-period
		// SMA.
		Rule exitRule = new UnderIndicatorRule(shortSma, longSma) // Trend
				.and(new CrossedUpIndicatorRule(rsi, Decimal.valueOf(95))) // Signal
																			// 1
				.and(new UnderIndicatorRule(shortSma, closePrice)); // Signal 2

		// TODO: Finalize the strategy

		return new Strategy(entryRule, exitRule);
	}

	public static Strategy buildCCIStrategy(TimeSeries series) {
		if (series == null) {
			throw new IllegalArgumentException("Series cannot be null");
		}

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		CCIIndicator longCci = new CCIIndicator(series, 200);
		CCIIndicator shortCci = new CCIIndicator(series, 5);
		Decimal plus100 = Decimal.HUNDRED;
		Decimal minus100 = Decimal.valueOf(-100);

		Rule entryRule = new OverIndicatorRule(longCci, plus100) // Bull trend
				.and(new UnderIndicatorRule(shortCci, minus100)); // Signal

		Rule exitRule = new UnderIndicatorRule(longCci, minus100)
		// Bear trend
				.and(new OverIndicatorRule(shortCci, plus100)); // Signal
																// 2
																// //
																// Signal

		Strategy strategy = new Strategy(entryRule, exitRule);
		strategy.setUnstablePeriod(5);
		return strategy;
	}

	/**
	 * @param series
	 *            a time series
	 * @return a moving momentum strategy
	 */
	public static Strategy buildMovingMomentumStrategy(TimeSeries series) {
		if (series == null) {
			throw new IllegalArgumentException("Series cannot be null");
		}

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

		// The bias is bullish when the shorter-moving average moves above the
		// longer moving average.
		// The bias is bearish when the shorter-moving average moves below the
		// longer moving average.
		EMAIndicator shortEma = new EMAIndicator(closePrice, 9);
		EMAIndicator longEma = new EMAIndicator(closePrice, 26);

		StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(
				series, 14);

		MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);
		EMAIndicator emaMacd = new EMAIndicator(macd, 18);

		// Entry rule
		Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
				.and(new CrossedDownIndicatorRule(stochasticOscillK, Decimal
						.valueOf(20))) // Signal 1
				.and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

		// Exit rule
		Rule exitRule = new UnderIndicatorRule(shortEma, longEma)
		// Trend
				.and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal
						.valueOf(80)))
				// Signal 1
				.and(new UnderIndicatorRule(macd, emaMacd)); // Signal
																// 2

		return new Strategy(entryRule, exitRule);
	}

	public static Strategy buildNilsStrategy(TimeSeries series) {

		// long 30 short 20 sma <50 short -> crossup
		if (series == null) {
			throw new IllegalArgumentException("Series cannot be null");
		}

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		EMAIndicator shortEma = new EMAIndicator(closePrice, 12);
		EMAIndicator longEma = new EMAIndicator(closePrice, 26);
		SMAIndicator sma = new SMAIndicator(closePrice, 50);

		Rule entryRule = new CrossedDownIndicatorRule(longEma, shortEma);// Signal
																			// 1

		// Exit rule
		Rule exitRule = new CrossedUpIndicatorRule(longEma, shortEma); // Signal
																		// 1

		return new Strategy(entryRule, exitRule);

	}

	public static Strategy buildFishStrategy(TimeSeries series, int i, int j,
			boolean isLong) {
		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		MedianPriceIndicator medianPrice = new MedianPriceIndicator(series);

		FisherIndicator shortFish;
		DelayIndicator delayFish;
		Rule minGain = stopGain(closePrice);
		Rule minDown = stopLoss(closePrice);

		shortFish = new FisherIndicator(medianPrice, i);
		delayFish = new DelayIndicator(shortFish, j);

		Rule entryRule = new CrossedDownIndicatorRule(delayFish, shortFish);// Signal

		Rule exitRule = new CrossedUpIndicatorRule(delayFish, shortFish);

		if (isLong) {
			exitRule = exitRule.and(minGain);
		} else {
			Rule temp = entryRule.and(minDown);
			entryRule = exitRule;
			exitRule = temp;
		}

		Strategy strategy = new Strategy(entryRule, exitRule);

		return strategy;

	}

	public static Strategy buildDanielStrategy(TimeSeries series) {

		if (series == null) {
			throw new IllegalArgumentException("Series cannot be null");
		}

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

		// The bias is bullish when the shorter-moving average moves above the
		// longer moving average.
		// The bias is bearish when the shorter-moving average moves below the
		// longer moving average.
		EMAIndicator shortEma = new EMAIndicator(closePrice, 20);
		EMAIndicator longEma = new EMAIndicator(closePrice, 30);

		StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(
				series, 14);

		MACDIndicator macd = new MACDIndicator(closePrice, 12, 26);
		EMAIndicator emaMacd = new EMAIndicator(macd, 9);

		// Entry rule
		Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
				.and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

		// Exit rule
		Rule exitRule = new UnderIndicatorRule(shortEma, longEma)
		// Trend
				.and(new UnderIndicatorRule(macd, emaMacd)); // Signal
																// 2//
																// Signal
																// 2

		return new Strategy(entryRule, exitRule);

	}

	public static Map<Strategy, String> buildEMAStrategiesMap(TimeSeries series) {
		HashMap<Strategy, String> strategies = new HashMap<Strategy, String>();
		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		Rule minGain = stopGain(closePrice);
		Rule minDown = stopLoss(closePrice);
		for (int i = 2; i < 50; i++) {
			for (int j = 2; j < 50; j++) {
				EMAIndicator shortEma = new EMAIndicator(closePrice, i);
				EMAIndicator longEma = new EMAIndicator(closePrice, j);
				SMAIndicator sma = new SMAIndicator(closePrice, 50);

				Rule entryRule = new CrossedDownIndicatorRule(longEma, shortEma)
						.and(minGain);// Signal
				// 1

				// Exit rule

				Rule exitRule = new CrossedUpIndicatorRule(longEma, shortEma)
						.and(minDown); // Signal
				// 1

				strategies.put(new Strategy(entryRule, exitRule), i + ":" + j);

			}

		}
		return strategies;
	}

	public static Map<Strategy, String> buildLongFishStrategiesMap(
			TimeSeries series) {
		HashMap<Strategy, String> strategies = new HashMap<Strategy, String>();

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		MedianPriceIndicator medianPrice = new MedianPriceIndicator(series);

		FisherIndicator shortFish;
		DelayIndicator delayFish;
		Rule minGain = stopGain(closePrice);
		Rule minDown = stopLoss(closePrice);

		for (int i = 5; i < 50; i++) {
			for (int j = 1; j < 3; j++) {
				shortFish = new FisherIndicator(medianPrice, i);
				delayFish = new DelayIndicator(shortFish, j);

				Rule entryRule = new CrossedDownIndicatorRule(delayFish,
						shortFish);// Signal
				// 1

				// Exit rule

				Rule exitRule = new CrossedUpIndicatorRule(delayFish, shortFish)
						.and(minGain);// Signal
				// 1

				strategies
						.put(new Strategy(entryRule, exitRule), i + "-:-" + j);

			}

		}
		return strategies;
	}

	
	public static double feeTolerance = 0.000;
	/**
	 * @param closePrice
	 * @return
	 */
	protected static StopLossRule stopLoss(ClosePriceIndicator closePrice) {
		return new StopLossRule(closePrice, Decimal.valueOf(1.0 - feeTolerance));
	}

	/**
	 * @param closePrice
	 * @return
	 */
	protected static StopGainRule stopGain(ClosePriceIndicator closePrice) {
		return new StopGainRule(closePrice, Decimal.valueOf(1 + feeTolerance));
	}

	/*
	 * public static void testStrategy(Strategies strt) { DataOverlord.strat =
	 * null; if (strt == Strategies.MovingMomentumStrategy) { DataOverlord.strat
	 * = buildMovingMomentumStrategy(DataOverlord.series); } else if (strt ==
	 * Strategies.NilsStrategy) { DataOverlord.strat =
	 * buildNilsStrategy(DataOverlord.series); } else if (strt ==
	 * Strategies.DanielStrategy) { DataOverlord.strat =
	 * buildDanielStrategy(DataOverlord.series);
	 * 
	 * } else if (strt == Strategies.CCIStrategy) { DataOverlord.strat =
	 * buildCCIStrategy(DataOverlord.series);
	 * 
	 * } else if (strt == Strategies.RSIStrategy) { DataOverlord.strat =
	 * buildRSIStrategy(DataOverlord.series); } else if (strt ==
	 * Strategies.findBest) { DataOverlord.strat =
	 * TradingBot.findBest(DataOverlord.series, strt);
	 * 
	 * } else if (strt == Strategies.findBestFish) { DataOverlord.strat =
	 * TradingBot.findBest(DataOverlord.series, strt); } else if (strt ==
	 * Strategies.FishStrategy) { DataOverlord.strat =
	 * buildFishStrategy(DataOverlord.series); }
	 * 
	 * // Running the strategy Logger.normalLog("" +
	 * DataOverlord.series.getTimePeriod().toString()); TradingRecord
	 * tradingRecord = DataOverlord.series .run(DataOverlord.strat);
	 * DataOverlord.res1 = "Number of trades for the strategy: " +
	 * tradingRecord.getTradeCount(); Logger.normalLog(DataOverlord.res1);
	 * 
	 * DataOverlord.res2 = "Total profit for the strategy: " + new
	 * TotalProfitCriterion().calculate(DataOverlord.series, tradingRecord);
	 * Logger.normalLog(DataOverlord.res2);
	 * CashFlowToChart.build(DataOverlord.series, DataOverlord.strat);
	 * 
	 * }
	 */
	static Strategy findBest(TimeSeries series2, Strategies strt) {
		Strategy bestS = null;
		double bestP = 0;
		String bestN = null;

		// Building the map of strategies
		Map<Strategy, String> strategies = null;
		if (strt == Strategies.findBest) {
			strategies = buildEMAStrategiesMap(series2);

		} else if (strt == Strategies.findBestFish) {
			strategies = buildLongFishStrategiesMap(series2);
		}
		// The analysis criterion
		bestS = findBestFromMap(series2, strategies);
		return bestS;
	}

	static Strategy findBestFromMap(TimeSeries series2,
			Map<Strategy, String> strategies) {
		Strategy bestS = null;
		double bestP = 0;
		String bestN = null;

		// The analysis criterion
		AnalysisCriterion profitCriterion = new TotalProfitCriterion();

		for (Map.Entry<Strategy, String> entry : strategies.entrySet()) {
			Strategy strategy = entry.getKey();
			String name = entry.getValue();
			double otherProfit = 0;
			// For each strategy...
			if (strategy.getClass().equals(ReversableStrategy.class)) {
				Logger.verboseLog("Strat is reversable");
				TradingRecord shortRecord = series2.run(
						((ReversableStrategy) strategy).getReversedStrategy(),
						OrderType.SELL);
				otherProfit = profitCriterion.calculate(series2, shortRecord) - 1;
				Logger.verboseLog("" + otherProfit);
			}
			TradingRecord longRecord = series2.run(strategy);
			double profit = profitCriterion.calculate(series2, longRecord);
			profit += otherProfit;
			if (profit > bestP) {
				bestP = profit;
				bestS = strategy;
				bestN = name;
			}
			Logger.verboseLog("\tProfit for " + name + ": " + profit);
		}
		TradingRecord tradingRecord = series2.run(bestS);
		double profit = profitCriterion.calculate(series2, tradingRecord);

		Logger.normalLog("\tBEST Profit for " + bestN + ": " + profit);

		return bestS;
	}
}
