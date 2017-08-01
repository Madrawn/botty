package de.daniel.dengler.getRichBot;

import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class ChartData {
	double dailyHigh, dailyLow;
	Tick latestTick;
	TimeSeries currentChart;

	public Tick getLatestTick() {
		latestTick = currentChart.getLastTick();
		return latestTick;
	}

	public double getDailyHigh() {
		double highest = 0;
		int numberOfTicks = (currentChart.getEnd() - currentChart.getBegin()) + 1;
		for (int i = currentChart.getBegin(); i < numberOfTicks
				+ currentChart.getBegin(); i++) {
			highest = Math.max(highest, currentChart.getTick(i).getMaxPrice()
					.toDouble());

		}

		return highest;
	}

	public double getDailyLow() {
		double lower = getCurrentPrice();
		int numberOfTicks = (currentChart.getEnd() - currentChart.getBegin()) + 1;
		for (int i = currentChart.getBegin(); i < numberOfTicks
				+ currentChart.getBegin(); i++) {
			lower = Math.min(lower, currentChart.getTick(i).getMaxPrice()
					.toDouble());

		}

		return lower;
	}

	public double getCurrentPrice() {
		return currentChart.getLastTick().getClosePrice().toDouble();
	}

	public TimeSeries getTimeSeries() {
		// TODO Auto-generated method stub
		return currentChart;
	}

}
