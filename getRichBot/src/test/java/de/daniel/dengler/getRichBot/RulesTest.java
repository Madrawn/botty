package de.daniel.dengler.getRichBot;

import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;

public class RulesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		long i = 1;
		TimeSeries t = new TimeSeries(new Period(5000));
		TradingRecord tr = new TradingRecord();
		t.addTick(new Tick(new DateTime(i++ * 5000), 5, 5, 2, 2, 1));
		Rule gain = StrategyBuilder.stopGain(new ClosePriceIndicator(t));
		System.out.println(gain.isSatisfied(t.getEnd(), tr));
		t.addTick(new Tick(new DateTime(i++ * 5000), 5, 5, 2, 3, 1));
		tr.enter(t.getEnd(),Decimal.valueOf("2"), Decimal.NaN );
		System.out.println(gain.isSatisfied(t.getEnd(), tr));
		
		System.out.println(Decimal.valueOf(2807.5).multipliedBy(Decimal.valueOf(0.995)));
		System.out.println(Decimal.valueOf(2807.5).multipliedBy(Decimal.valueOf(1.005)));
		
		assertTrue(gain.isSatisfied(t.getEnd(), tr));
	}
}
