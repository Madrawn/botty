package de.daniel.dengler.getRichBot;

import java.awt.Canvas;
import java.awt.Graphics;

import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesPainter {
	
	Canvas painting;
	
	public TimeSeriesPainter(TimeSeries serie, int spacing, int maxHeight) {
		painting = new Canvas();
		Graphics g = painting.getGraphics();
	}

	public int getMinWidth() {
		// TODO Auto-generated method stub
		return painting.getWidth();
	}

}
