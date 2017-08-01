package de.daniel.dengler.getRichBot;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jfree.data.time.Minute;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.util.ArrayUtilities;
import org.joda.time.DateTime;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class Helper {

	public static TimeSeries convertIntoTimeseries(ChartDataPoint[] points) {
		//check order of points and reverse if neccesarry 
		if(points[0].date>points[1].date)
			ArrayUtils.reverse(points);
		List<Tick> ticks = new LinkedList<Tick>();
		if (points != null) {

			for (int i = 0; i < points.length; i++) {
				ChartDataPoint point = points[i];
				Tick tick = new Tick(new DateTime(point.date), point.open,
						point.high, point.low, point.close, point.volume);
				ticks.add(tick);
			}
		}
		TimeSeries t = new TimeSeries(ticks);
		return t;

	}
	
    /**
     * Builds a JFreeChart OHLC dataset from a ta4j time series.
     * @param series a time series
     * @return an Open-High-Low-Close dataset
     */
    public static OHLCDataset createOHLCDataset(TimeSeries series) {
        final int nbTicks = series.getTickCount();
        
        Date[] dates = new Date[nbTicks];
        double[] opens = new double[nbTicks];
        double[] highs = new double[nbTicks];
        double[] lows = new double[nbTicks];
        double[] closes = new double[nbTicks];
        double[] volumes = new double[nbTicks];
        
        for (int i = 0; i < nbTicks; i++) {
            Tick tick = series.getTick(i);
            dates[i] = new Date(tick.getEndTime().getMillis());
            opens[i] = tick.getOpenPrice().toDouble();
            highs[i] = tick.getMaxPrice().toDouble();
            lows[i] = tick.getMinPrice().toDouble();
            closes[i] = tick.getClosePrice().toDouble();
            volumes[i] = tick.getVolume().toDouble();
        }
        
        OHLCDataset dataset = new DefaultHighLowDataset("btc", dates, highs, lows, opens, closes, volumes);
        
        return dataset;
    }
    
    public static org.jfree.data.time.TimeSeries buildChartTimeSeries(TimeSeries tickSeries, Indicator<Decimal> indicator, String name) {
        org.jfree.data.time.TimeSeries chartTimeSeries = new org.jfree.data.time.TimeSeries(name);
        for (int i = 0; i < tickSeries.getTickCount(); i++) {
            Tick tick = tickSeries.getTick(i);
            chartTimeSeries.addOrUpdate(new Minute(tick.getEndTime().toDate()), indicator.getValue(i).toDouble());
        }
        return chartTimeSeries;
    }

}
