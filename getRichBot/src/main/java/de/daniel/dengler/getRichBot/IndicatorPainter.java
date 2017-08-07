package de.daniel.dengler.getRichBot;

import java.awt.Color;
import java.awt.Graphics;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class IndicatorPainter {
	double[] minMax;
	private Indicator<Decimal> serie;
	private int width;
	private int height;
	private Color color;

	public IndicatorPainter(Indicator<Decimal> serie, int width, int height,
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
		for (int i = 0; i < serie.getTimeSeries().getTickCount(); i++) {
			int xPos;
			if (i != 0) {
				xPos = i * ticksEveryXPixels;
			} else {
				xPos = 0;
			}
			int yPos;
			int diffY = (int) (minMax[1] - minMax[0]);
			double valueThisTick = serie.getValue(i).toDouble();
			double pricePercent = (valueThisTick - diffY) / minMax[1];
			yPos = (int) (height * pricePercent);
			g.fillRect(xPos, yPos, ticksSize, ticksSize);
		}

	}

	private double[] getTimeSeriesMinMax(Indicator<Decimal> serie2) {
		double min = 1e99, max = 0;
		for (int i = 0; i < serie2.getTimeSeries().getTickCount(); i++) {
			min = Math.min(min, serie2.getValue(i).toDouble());
			max = Math.max(max, serie2.getValue(i).toDouble());

		}
		double[] minMax = { min, max };
		return minMax;
	}
}
