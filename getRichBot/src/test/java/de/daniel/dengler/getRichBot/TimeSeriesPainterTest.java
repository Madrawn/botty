package de.daniel.dengler.getRichBot;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesPainterTest {
	
	
	@Test
	public void setupTest() {
		Tick[] ticks = generateTicks(5);
		List<Tick> tickList = Arrays.asList(ticks);
		TimeSeries serie = new TimeSeries(tickList);
		int spacing = 5;
		TimeSeriesPainter painter = new TimeSeriesPainter(serie, spacing, heigth);
		int expectedWidth = (serie.getTickCount() -1 * spacing) + serie.getTickCount();
		int actualWidth = painter.getMinWidth();
		Assert.assertEquals("Not as wide as expected", expectedWidth, actualWidth);
	}

	private Tick[] generateTicks(int numTicks) {
		Tick[] result = new Tick[numTicks];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = new Tick(new DateTime(i*5000), Math.random() * 100, Math.random() * 100, Math.random() * 100, Math.random() * 100, Math.random() * 100);
		}
		return null;
	}
	

}
