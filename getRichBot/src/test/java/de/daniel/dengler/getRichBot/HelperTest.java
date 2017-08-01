package de.daniel.dengler.getRichBot;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class HelperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void ConvertIntoTimeSeriestest() {
		ChartDataPoint point1 = new ChartDataPoint();
		point1.setAll(1l,5,4,2,2,2);
		
		ChartDataPoint point2 = new ChartDataPoint();
		point2.setAll(12,6,5,2,2,2);
		ChartDataPoint point3 = new ChartDataPoint();
		point3.setAll(13,7,6,2,2,2);
		ChartDataPoint[] points = new ChartDataPoint[]{
			point1,point2,point3	
		};
		
		TimeSeries result = Helper.convertIntoTimeseries(points);
		Tick firstTick = result.getFirstTick(), lastTick = result.getLastTick();
		Double expectation = 5d;
		assertTrue("Expected 5 got " + firstTick.getMaxPrice().toDouble(), expectation.equals(firstTick.getMaxPrice().toDouble()));
		
	}

}
