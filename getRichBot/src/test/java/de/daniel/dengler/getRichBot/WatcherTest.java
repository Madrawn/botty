package de.daniel.dengler.getRichBot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.daniel.dengler.getRichBot.UI.LeftListItem;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class WatcherTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * The watcher should be able to get me the latest candle
	 */
	@Test
	public void watcherGetCandle() {
		Watcher w = new Watcher(new LeftListItem(),
				new TestWrapper());

		Candle c = w.getLatestCandle();
		assertNotNull(c);
		//Candle should have not 0 in all its fields
		double sum = c.getClose()+c.getHigh()+c.getLow()+c.getOpen()+c.getPeriod();
		assertTrue(sum != 0);
		//prices can't be negative
		assertTrue(c.getClose() > 0);
		assertTrue(c.getOpen() > 0);
		assertTrue(c.getHigh() > 0);
		assertTrue(c.getLow() > 0);
		assertTrue(c.getStartDate() > 0);
		assertTrue(c.getPeriod() > 0);
	}
	
	/**
	 * Watcher should be able to get me a filled timeseries
	 */
	@Test
	public void watcherGetChart() {
		Watcher w = new Watcher(new LeftListItem(),
				new TestWrapper());
		TimeSeries t = w.chartData.currentChart;
		assertNotNull(t);
		//TimeSeries shouldn't be empty
		int endIndex = t.getEnd();
		assertTrue(endIndex > 0);
	}
	
	
	/**
	 * Watcher should get a new tick eventually
	 */
	@Test
	public void watcherUpdateTest(){
		Watcher w = new Watcher(new LeftListItem(),
				new TestWrapper());
		Tick firstTick = w.chartData.getLatestTick();
		Tick secondTick = w.chartData.getLatestTick();
		assertEquals(firstTick, secondTick);
		w.update();
		secondTick = w.chartData.getLatestTick();
		assertNotEquals(firstTick, secondTick);
		
	}

}
