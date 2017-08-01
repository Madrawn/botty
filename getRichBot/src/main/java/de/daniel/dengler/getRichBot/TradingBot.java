package de.daniel.dengler.getRichBot;

import java.util.Map;
import java.util.Map.Entry;

import de.daniel.dengler.getRichBot.BackTradingFrame.Strategies;
import de.daniel.dengler.getRichBot.exchangewrappers.ExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.trading.rules.StopGainRule;
import eu.verdelhan.ta4j.trading.rules.StopLossRule;

/**
 * This should be able to trade. It starts with .startTrading() and stops with
 * .stopTrading() It should keep track of it's own balance. It should act upon
 * the strategy
 * 
 * @author Madrawn
 *
 */
public class TradingBot {

	Watcher w;
	Strategy myStrat;
	boolean running;

	public TradingBot(Watcher w, Strategy myStrat) {
		this.w = w;
		this.myStrat = myStrat;

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
	 * @deprecated Use {@link StrategyBuilder#buildFishStrategy(TimeSeries,int,int,boolean)} instead
	 */
	public static Strategy buildFishStrategy(TimeSeries series, int i, int j, boolean isLong) {
		return StrategyBuilder.buildFishStrategy(series, i, j, isLong);
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
	public static Map<Strategy, String> buildLongFishStrategiesMap(
			TimeSeries series) {
				return StrategyBuilder.buildLongFishStrategiesMap(series);
			}

	/**
	 * @param closePrice
	 * @return
	 * @deprecated Use {@link StrategyBuilder#stopLoss(ClosePriceIndicator)} instead
	 */
	protected static StopLossRule stopLoss(ClosePriceIndicator closePrice) {
		return StrategyBuilder.stopLoss(closePrice);
	}

	/**
	 * @param closePrice
	 * @return
	 * @deprecated Use {@link StrategyBuilder#stopGain(ClosePriceIndicator)} instead
	 */
	protected static StopGainRule stopGain(ClosePriceIndicator closePrice) {
		return StrategyBuilder.stopGain(closePrice);
	}
/*
		public static void testStrategy(Strategies strt) {
			DataOverlord.strat = null;
			if (strt == Strategies.MovingMomentumStrategy) {
				DataOverlord.strat = buildMovingMomentumStrategy(DataOverlord.series);
			} else if (strt == Strategies.NilsStrategy) {
				DataOverlord.strat = buildNilsStrategy(DataOverlord.series);
			} else if (strt == Strategies.DanielStrategy) {
				DataOverlord.strat = buildDanielStrategy(DataOverlord.series);
	
			} else if (strt == Strategies.CCIStrategy) {
				DataOverlord.strat = buildCCIStrategy(DataOverlord.series);
	
			} else if (strt == Strategies.RSIStrategy) {
				DataOverlord.strat = buildRSIStrategy(DataOverlord.series);
			} else if (strt == Strategies.findBest) {
				DataOverlord.strat = TradingBot.findBest(DataOverlord.series, strt);
	
			} else if (strt == Strategies.findBestFish) {
				DataOverlord.strat = TradingBot.findBest(DataOverlord.series, strt);
			} else if (strt == Strategies.FishStrategy) {
				DataOverlord.strat = buildFishStrategy(DataOverlord.series);
			}
	
			// Running the strategy
			Logger.normalLog("" + DataOverlord.series.getTimePeriod().toString());
			TradingRecord tradingRecord = DataOverlord.series
					.run(DataOverlord.strat);
			DataOverlord.res1 = "Number of trades for the strategy: "
					+ tradingRecord.getTradeCount();
			Logger.normalLog(DataOverlord.res1);
	
			DataOverlord.res2 = "Total profit for the strategy: "
					+ new TotalProfitCriterion().calculate(DataOverlord.series,
							tradingRecord);
			Logger.normalLog(DataOverlord.res2);
			CashFlowToChart.build(DataOverlord.series, DataOverlord.strat);
	
		}
	*/
		/**
		 * @deprecated Use {@link StrategyBuilder#findBest(TimeSeries,Strategies)} instead
		 */
		static Strategy findBest(TimeSeries series2, Strategies strt) {
			return StrategyBuilder.findBest(series2, strt);
		}

	/**
	 * @deprecated Use {@link StrategyBuilder#findBestFromMap(TimeSeries,Map<Strategy, String>)} instead
	 */
	static Strategy findBestFromMap(TimeSeries series2,
			Map<Strategy, String> strategies) {
				return StrategyBuilder.findBestFromMap(series2, strategies);
			}

}
