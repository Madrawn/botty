package de.daniel.dengler.getRichBot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePeriodLengths;
import de.daniel.dengler.getRichBot.enums.PossibleExchanges;
import de.daniel.dengler.getRichBot.enums.StrategyType;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexBalance;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexWrapperConnector;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexWrapperSettings;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.helpers.AverageTrueRangeIndicator;
import eu.verdelhan.ta4j.indicators.oscillators.FisherIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.simple.MedianPriceIndicator;
import eu.verdelhan.ta4j.trading.rules.WaitForRule;

public class SuperEasyCmdBot implements Observer {

	// command.jar -c BTCUSD -m

	private static Balance balance;
	private static String currentVersion = "v0.001";
	private static Options options;
	static CommandLine cmd;

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		options = new Options();

		Option currencyPair = OptionBuilder.withArgName("currency pair")
				.hasArg()
				.withDescription("the currency pair which should be traded")
				.withLongOpt("currency-pair").create("c");

		Option tradingType = OptionBuilder.withArgName("trading type").hasArg()
				.withDescription("the type of trading: margin or funding")
				.withLongOpt("trading-type").create("t");

		Option apiKey = OptionBuilder
				.withArgName("file")
				.hasArg()
				.withDescription(
						"A file containing api key and secret as json text")
				.withLongOpt("api-file").create("f");

		Option targetExchange = OptionBuilder
				.withArgName("exchange")
				.hasArg()
				.withDescription(
						"the exchange you want to connect to. Currently only BITFINEX working")
				.withLongOpt("exchange").create("e");

		Option tradingStrategy = OptionBuilder.withArgName("strategy")
				.hasArgs().withDescription("the strategy which should be used")
				.withLongOpt("strategy").create("s");

		Option numberOfPeriods = OptionBuilder.withArgName("num periods")
				.hasArg().withDescription("the number of periods to hold")
				.withLongOpt("num-periods").create("n");

		Option periodLength = OptionBuilder.withArgName("period length")
				.hasArg()
				.withDescription("the length of the candles to get in seconds")
				.withLongOpt("period-length").create("l");

		Option realtrade = new Option("r", "real-trade", false,
				"if actual buy and sell order should be made");
		Option help = new Option("help", "print this message");
		Option version = new Option("version",
				"print the version information and exit");
		Option quiet = new Option("quiet", "be extra quiet");
		Option verbose = new Option("verbose", "be extra verbose");
		Option debug = new Option("debug", "print debugging information");
		Option dontask = new Option("dontask",
				"don't ask for confirmation when placing orders");
		Option reverseRule = new Option("reverse",
				"Uses the strategy reversed for short trading");
		Option useATR = new Option("ATR",
				"uses average true range to stop trading when atr is smaller than 0.2% fee");

		options.addOption(useATR);
		options.addOption(reverseRule);
		options.addOption(dontask);
		options.addOption(numberOfPeriods);
		options.addOption(periodLength);
		options.addOption(currencyPair);
		options.addOption(tradingType);
		options.addOption(apiKey);
		options.addOption(targetExchange);
		options.addOption(realtrade);
		options.addOption(help);
		options.addOption(quiet);
		options.addOption(verbose);
		options.addOption(debug);
		options.addOption(version);
		options.addOption(tradingStrategy);

		CommandLineParser parser = new org.apache.commons.cli.BasicParser();
		try {
			cmd = parser.parse(options, args);

			Logger.debug = cmd.hasOption("debug");
			Logger.quiet = cmd.hasOption("quiet");
			Logger.verbose = cmd.hasOption("verbose");

			if (cmd.hasOption("help")) {
				// automatically generate the help statement
				printHelp(options);
			} else if (cmd.getOptions().length == 0) {
				printHelp(options);
			} else if (cmd.hasOption("version")) {
				System.out.println(currentVersion);

			} else {
				Logger.verboseLog("Reading options & constructing the exchange wrapper");
				IExchangeWrapper wrappy = setUpWrapper(cmd);
				Watcher wat = new Watcher(wrappy, new SuperEasyCmdBot());
				new Thread(wat).start();
				Logger.debugLog("Setup finished, leaving main");

				/*
				 * PossibleExchanges exchange = getExchange(cmd);
				 * 
				 * 
				 * if (exchange != null) {
				 * 
				 * IExchangeWrapper wrapper = new
				 * BitfinexExchangeWrapper(settings, conn); } else {
				 * debugLog("Exchange is null", cmd); }
				 */
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static IExchangeWrapper setUpWrapper(CommandLine cmd) {
		Logger.verboseLog("Setting wrapper up");
		PossibleExchanges ex = getTheExchange(cmd);
		if (ex.equals(PossibleExchanges.BITFINEX)) {
			int numP = getPNum(cmd);
			BitfinexPossiblePairs pair = getPair(cmd, ex);
			BitfinexPossiblePeriodLengths pLength = getPLength(cmd, ex);
			BitfinexWrapperSettings settings = new BitfinexWrapperSettings(
					pair, pLength, numP);
			BitfinexWrapperConnector conny = new BitfinexWrapperConnector();
			BitfinexExchangeWrapper wrappy = new BitfinexExchangeWrapper(
					settings, conny);
			Logger.verboseLog("Wrapper set up done");
			return wrappy;

		}
		Logger.warningLog("Could not set up the wrapper");
		return null;
	}

	private static BitfinexPossiblePeriodLengths getPLength(CommandLine cmd,
			PossibleExchanges ex) {

		if (cmd.hasOption("l")) {
			BitfinexPossiblePeriodLengths result = BitfinexPossiblePeriodLengths
					.valueOf(cmd.getOptionValue("l"));
			return result;
		}

		BitfinexPossiblePeriodLengths def = BitfinexPossiblePeriodLengths.MIN_1;
		Logger.normalLog("Defaulting to " + def);
		return def;
	}

	private static BitfinexPossiblePairs getPair(CommandLine cmd,
			PossibleExchanges ex) {
		if (cmd.hasOption("c")) {
			BitfinexPossiblePairs result = BitfinexPossiblePairs.valueOf(cmd
					.getOptionValue("c").toUpperCase());
			return result;
		}

		BitfinexPossiblePairs btcusd = BitfinexPossiblePairs.BTCUSD;
		Logger.normalLog("Defaulting to " + btcusd);
		return btcusd;
	}

	private static int getPNum(CommandLine cmd) {
		if (cmd.hasOption("n")) {
			int res = Integer.parseInt(cmd.getOptionValue("n"));
			Logger.normalLog(String.format("Seting number of periods to %s",
					res));
			return res;
		}

		// default
		int def = 500;
		Logger.normalLog("Defaulting period number to " + def);
		return def;
	}

	private static PossibleExchanges getTheExchange(CommandLine cmd) {
		PossibleExchanges result = null;
		if (cmd.hasOption("e")) {
			if (cmd.getOptionValue("e").toLowerCase().equals("bitfinex")) {
				result = PossibleExchanges.BITFINEX;
			} else {
				Logger.warningLog(String.format(
						"Found no matching exchange for %s!",
						cmd.getOptionValue("e")));
			}

		} else {
			Logger.log("-e was not set defaulting to Bitfinex");
			result = PossibleExchanges.BITFINEX;
		}
		Logger.verboseLog(String.format("Set %s as exchange", result));
		return result;
	}

	/**
	 * @param options
	 */
	protected static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("SuperEasyCmdBot", options);
	}

	private Strategy longStrat, shortStrat;
	private boolean flipFlop;

	public void update(Observable o, Object arg) {
		Logger.debugLog(String.format("Update was called with %s", arg));
		Watcher w = (Watcher) o;
		if (arg != null && arg.equals("init")) {

			w.tradingRecord = new TradingRecord();
			Logger.verboseLog("Init the strat");
			if (cmd.hasOption("s")) {
				String[] optionValues = cmd.getOptionValues("s");
				StrategyType type = StrategyType.valueOf(optionValues[0]);
				if (type != null) {
					if (type.equals(StrategyType.LONGSHORTFISHER)) {
						Logger.debugLog(String.format(
								"Experimental %s chosen with %s and %s", type,
								optionValues[1], optionValues[2]));
						Strategy[] strats = StrategyBuilder.longShortFisher(
								w.getTimeSeries(),
								Integer.parseInt(optionValues[1]),
								Integer.parseInt(optionValues[2]));
						longStrat = strats[0];
						shortStrat = strats[1];
					} else {

						String[] args = Arrays.copyOfRange(optionValues, 1,
								optionValues.length);
						longStrat = new StrategyBuilder().buildStrategy(type,
								w.getTimeSeries(), args);
					}

				} else {
					Logger.warningLog(String
							.format("%s is not known, try %s\n defaulting to AUTO_FISHER",
									optionValues[0], StrategyType.values()));
					longStrat = new StrategyBuilder().buildStrategy(
							StrategyType.AUTO_FISHER, w.getTimeSeries(),
							(String) null);

				}

			} else {
				Logger.normalLog("Defaulting to AUTO_FISHER");
				longStrat = new StrategyBuilder().buildStrategy(
						StrategyType.AUTO_FISHER, w.getTimeSeries(),
						(String) null);

			}
			w.setLongStrat(longStrat);
			if (shortStrat != null) {
				w.setShortStrat(shortStrat);
			}

		} else {
			Logger.verboseLog("Got update from the Watcher");
			whatWouldTheStratDo(w);

		}

		Logger.debugLog("Update has ended");
	}

	private void whatWouldTheStratDo(Watcher w) {
		Strategy longStrategy = w.getLongStrat();
		Strategy shortStrategy = w.getShortStrat();
		TradingRecord t = w.getTradingRecord();
		Logger.debugLog(String.format("TrainingRec: %s", t));

		boolean longEnter, longExit, shortEnter = false, shortExit = false;

		int end = w.getTimeSeries().getEnd();
		longEnter = longStrategy.shouldEnter(end, t);
		longExit = longStrategy.shouldExit(end, t);

		ClosePriceIndicator closePrice = new ClosePriceIndicator(
				w.getTimeSeries());
		Rule gain = StrategyBuilder.stopGain(closePrice);
		Rule loss = StrategyBuilder.stopLoss(closePrice);

		Rule wait = new WaitForRule(OrderType.BUY, 5);

		FisherIndicator testFish = new FisherIndicator(
				new MedianPriceIndicator(w.getTimeSeries()), 9);

		Logger.debugLog(String.format("Fish: %s", testFish.getValue(end)));

		Logger.debugLog(String.format(
				"IsGainTrue: %s IsLossTrue: %s IsWaitTrue %s",
				gain.isSatisfied(end, t), loss.isSatisfied(end, t),
				wait.isSatisfied(end, t)));
		boolean satisfied;
		Trade currentTrade = t.getCurrentTrade();
		if (currentTrade.isOpened()) {
			Decimal entryPrice = currentTrade.getEntry().getPrice();
			Decimal currentPrice = closePrice.getValue(end);
			Decimal multipliedBy = entryPrice.multipliedBy(Decimal
					.valueOf(1.0 - StrategyBuilder.feeTolerance));
			satisfied = currentPrice.isLessThanOrEqual(multipliedBy);
			Logger.debugLog(String
					.format("Gain and or loss should fire at %s. Entry is %s current is %s",
							multipliedBy, entryPrice, currentPrice));

		}

		if (shortStrategy != null) {
			Logger.debugLog("Checking shorts");
			shortEnter = shortStrategy.shouldEnter(end, t);
			shortExit = shortStrategy.shouldExit(end, t);

		} else if (cmd.hasOption("reverse")) {
			shortEnter = longExit;
			shortExit = longEnter;
		}

		Logger.normalLog(String
				.format("Tick: %s \nLongEnter: %s LongExit: %s ShortEnter: %s ShortExit: %s",
						w.getTimeSeries().getLastTick(),
						Logger.colorBool(longEnter),
						Logger.colorBool(longExit),
						Logger.colorBool(shortEnter),
						Logger.colorBool(shortExit)));
		actuallyDoSomethingMaybe(w, longEnter, longExit, shortEnter, shortExit);
	}

	private void actuallyDoSomethingMaybe(Watcher w, boolean longEnter,
			boolean longExit, boolean shortEnter, boolean shortExit) {
		boolean forReal = cmd.hasOption("r");
		boolean newTrade = w.getTradingRecord().getCurrentTrade().isNew();
		if (balance == null && !forReal) {
			balance = new FakeBalance(50, 0.1);
		} else if (balance == null && forReal) {
			balance = new BitfinexBalance(w);
			w.setBalance(balance);
		}
		Logger.debugLog("IsNewTrade: " + newTrade);
		double sellPrice;
		double buyPrice;
		try {
			sellPrice = w.getCurrentBestBid();
			buyPrice = w.getCurrentBestAsk();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		boolean ATRSaysStop = false;
		if (cmd.hasOption("ATR")) {
			double avgPrice = (sellPrice + buyPrice) / 2;
			double avgFee = avgPrice * 0.002;
			AverageTrueRangeIndicator atrIndicator = new AverageTrueRangeIndicator(
					w.getTimeSeries(), 14);
			Decimal value = atrIndicator.getValue(w.getTimeSeries().getEnd());
			ATRSaysStop = value.isLessThan(Decimal.valueOf(avgFee));
			Logger.debugLog("ATR says Stop: " + ATRSaysStop);
			Logger.debugLog("AvgFee: " + avgFee);
			Logger.debugLog("ATR: " + value);
		}
		double coba = balance.getMaxSellCoBalance(), ba = balance
				.getMaxSellBalance();
		if (newTrade) {
			if (shortEnter && !ATRSaysStop) {
				w.getTradingRecord().enter(w.getTimeSeries().getEnd(),
						Decimal.valueOf(sellPrice), Decimal.NaN);
				balance.sellCoBa(sellPrice, coba);
				flipFlop = false;
			} else if (longEnter && !ATRSaysStop) {
				w.getTradingRecord().enter(w.getTimeSeries().getEnd(),
						Decimal.valueOf(buyPrice), Decimal.NaN);
				balance.buyCoBa(buyPrice, ba);
				flipFlop = true;
			}
		} else {

			Logger.debugLog(String.format("justOnce = %s", flipFlop));

			if (shortExit && !flipFlop) {
				w.getTradingRecord().exit(w.getTimeSeries().getEnd(),
						Decimal.valueOf(buyPrice), Decimal.NaN);
				balance.buyCoBa(buyPrice, ba);
				if (!ATRSaysStop) {
					w.getTradingRecord().enter(w.getTimeSeries().getEnd(),
							Decimal.valueOf(buyPrice), Decimal.NaN);
				}
				flipFlop = true;

			} else if (longExit && flipFlop) {
				w.getTradingRecord().exit(w.getTimeSeries().getEnd(),
						Decimal.valueOf(sellPrice), Decimal.NaN);
				balance.sellCoBa(sellPrice, coba);
				if (!ATRSaysStop) {
					w.getTradingRecord().enter(w.getTimeSeries().getEnd(),
							Decimal.valueOf(sellPrice), Decimal.NaN);
				}
				flipFlop = false;

			}
		}

		/*
		 * if (forReal) {
		 * 
		 * } else { if (fakeBalance == null) { fakeBalance = new FakeBalance(50,
		 * cmd); }
		 * 
		 * if (enter) { double buyPrice = w.getCurrentBestAsk();
		 * fakeBalance.fakeBuy(buyPrice); } else if (exit) { double sellPrice =
		 * w.getCurrentBestBid(); fakeBalance.fakeSell(sellPrice); } }
		 */
	}

}
