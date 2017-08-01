package de.daniel.dengler.getRichBot;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import de.daniel.dengler.getRichBot.enums.StrategyType;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;

public class Watcher extends Observable implements Runnable {

	IExchangeWrapper myWrapper;

	// LeftListItemController myLeftListItem;
	TradingBot myBot;
	ChartData chartData;
	Strategy longStrat, shortStrat;
	TradingRecord tradingRecord;
	Balance balance;

	public TradingRecord getTradingRecord() {
		return tradingRecord;
	}

	public void setTradingRecord(TradingRecord tradingRecord) {
		this.tradingRecord = tradingRecord;
	}

	public void setShortStrat(Strategy shortStrat) {
		this.shortStrat = shortStrat;
	}

	public Strategy getLongStrat() {
		return longStrat;
	}

	public void setLongStrat(Strategy myStrat) {
		this.longStrat = myStrat;
	}

	boolean continueRunning;


	public TradingBot getMyBot() {
		return myBot;
	}

	public void setMyBot(TradingBot myBot) {
		this.myBot = myBot;
	}

	public void createBot(TimeSeries series, StrategyType type) {

	}

	public Watcher(/* LeftListItemController con, */IExchangeWrapper wrap) {
		myWrapper = wrap;
		// myLeftListItem = con;
		init();

	}

	public Watcher(IExchangeWrapper wrappy, Observer superEasyCmdBot) {
		myWrapper = wrappy;
		// myLeftListItem = con;
		addObserver(superEasyCmdBot);
		init();
	}

	public void init() {
		chartData = new ChartData();
		chartData.currentChart = Helper.convertIntoTimeseries(myWrapper
				.getChart());
		chartData.dailyHigh = myWrapper.getDailyHigh();
		chartData.dailyLow = myWrapper.getDailyLow();
		Logger.debugLog("Notifying observers for the first time");
		setChanged();
		notifyObservers("init");
		/*
		 * myLeftListItem.setDailyHigh(chartData.getDailyHigh());
		 * myLeftListItem.setDailyLow(chartData.getDailyLow());
		 * myLeftListItem.setCurrencyPair
		 * (myWrapper.getSettings().getPairAsString());
		 * myLeftListItem.setExchange
		 * (myWrapper.getSettings().getExchangeName());
		 * myLeftListItem.setCurPrice(chartData.getCurrentPrice());
		 */

	}

	public Candle getLatestCandle() {
		return new Candle();
	}

	public void run() {
		int timeBetweenUpdates = myWrapper.getSettings().getPeriodLength() * 1000;
		continueRunning = true;
		long lastUpdate = 0, now;
		Logger.normalLog(String.format(" %s is starting %tc", this, new Date()));

		while (continueRunning) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date nowD = new Date();

			now = nowD.getTime();
			if (now > lastUpdate + timeBetweenUpdates - 10000) {
				Logger.normalLog(String.format(
						"Updating\nLast Update: %tc\nNow: %tc\nTime <->: %s",
						lastUpdate, now, timeBetweenUpdates));
				update();
				lastUpdate = new Date().getTime();
			}

		}

		Logger.normalLog(String.format("%s has closed %tc", this, new Date()));

	}

	public void interrupt() {
		this.continueRunning = false;
	}

	void update() {
		boolean updated = false;
		int sleepyTime = (myWrapper.getSettings().getPeriodLength() * 1000) / 10;
		while (!updated) {
			Tick myMostRecentTick = chartData.currentChart.getLastTick();
			Tick latestRemoteTick = chartData.currentChart.getLastTick();
			try {
				latestRemoteTick = myWrapper.getMostRecentCandle().toTick();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!myMostRecentTick.getEndTime().equals(
					latestRemoteTick.getEndTime())) {
				Logger.verboseLog(String.format(
						"Update successful\n old: %s\n new: %s",
						myMostRecentTick, latestRemoteTick));
				chartData.currentChart.addTick(latestRemoteTick);
				updated = true;
			} else {
				Logger.verboseLog(String.format(
						"No new Tick, chilling for %s seconds",
						sleepyTime / 1000));

				try {
					Thread.sleep(sleepyTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		onUpdate();
		updateUI();

	}

	private void onUpdate() {
		if (getMyBot() != null && getMyBot().running) {
			TradingBot bot = getMyBot();
			// Ask the bot what he recommends, but first we need to tell him
			// about what is going on

		}
	}

	private void updateUI() {
		setChanged();
		notifyObservers();
		/*
		 * myLeftListItem.setDailyHigh(chartData.getDailyHigh());
		 * myLeftListItem.setDailyLow(chartData.getDailyLow());
		 * myLeftListItem.setCurrencyPair
		 * (myWrapper.getSettings().getPairAsString());
		 * myLeftListItem.setExchange
		 * (myWrapper.getSettings().getExchangeName());
		 * myLeftListItem.setCurPrice(chartData.getCurrentPrice());
		 */
	}

	public TimeSeries getTimeSeries() {
		return chartData.getTimeSeries();
	}

	/*
	 * public LeftListItem getLeftListItem() { return
	 * myLeftListItem.getLeftListItem(); }
	 */

	public ChartData getChartData() {
		// TODO Auto-generated method stub
		return chartData;
	}

	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return myWrapper.getSettings();
	}

	public double getBalance(String currencyName) {

		return myWrapper.getBalance(currencyName);

	}

	public double getCurrentBestAsk() {
		// TODO Auto-generated method stub
		return myWrapper.getCurrentBestAsk();
	}

	public double getCurrentBestBid() {
		// TODO Auto-generated method stub
		return myWrapper.getCurrentBestBid();
	}

	public Strategy getShortStrat() {
		return shortStrat;
	}

	public void setBalance(Balance balance2) {
		this.balance = balance2;
		
	}

}
