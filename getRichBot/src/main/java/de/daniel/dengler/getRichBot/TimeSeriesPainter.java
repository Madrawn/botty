package de.daniel.dengler.getRichBot;

import java.awt.Color;
import java.awt.Graphics;

import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesPainter {
	double[] minMax;
	private TimeSeries serie;
	private int width;
	private int height;
	private Color color;

	public TimeSeriesPainter(TimeSeries serie, int width, int height,
			Graphics g, Color c) {
		minMax = getTimeSeriesMinMax(serie);
		this.color = c;
		this.serie = serie;
		this.width = width;
		this.height = height;
		draw(g);
	}

	private void draw(Graphics g) {
		int ticksEveryXPixels = width / height;
		int ticksSize = Math.max(1, ticksEveryXPixels / 3);
		g.setColor(color);
		for (int i = 0; i < serie.getTickCount(); i++) {
			Tick tick = serie.getTick(i);
			int xPos;
			if (i != 0) {
				xPos = i * ticksEveryXPixels;
			} else {
				xPos = 0;
			}
			int yPos;
			int diffY = (int) (minMax[1] - minMax[0]);
			double priceThisTick = tick.getClosePrice().toDouble();
			double pricePercent = (priceThisTick - diffY) / minMax[1];
			yPos = (int) (height * pricePercent);
			g.fillRect(xPos, yPos, ticksSize, ticksSize);
		}

	}

	private double[] getTimeSeriesMinMax(TimeSeries serie) {
		double min = 1e99, max = 0;
		for (int i = 0; i < serie.getTickCount(); i++) {
			Tick tick = serie.getTick(i);
			min = Math.min(min, tick.getClosePrice().toDouble());
			max = Math.max(max, tick.getClosePrice().toDouble());

		}
		double[] minMax = { min, max };
		return minMax;
	}

}
