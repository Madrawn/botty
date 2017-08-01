package de.daniel.dengler.getRichBot;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import de.daniel.dengler.getRichBot.exchangewrappers.PoloWrapper;

public class PoloWrapperTest {

	@Test
	public void test() {
		long start =  (new Date().getTime())/1000 - 3600;
		long end = (new Date().getTime())/1000;
		ChartDataPoint[] c = null;
		try {
			c = PoloWrapper.returnChartData("BTC_XMR", 300, start, end);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(c);
	}

}
