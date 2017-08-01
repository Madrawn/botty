package de.daniel.dengler.getRichBot;

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.analysis.CashFlow;
import eu.verdelhan.ta4j.indicators.oscillators.FisherIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.simple.MedianPriceIndicator;

/**
 * This class builds a graphical chart showing the cash flow of a strategy.
 */
public class CashFlowToChart {

    public static ApplicationFrame frame;

	/**
     * Builds a JFreeChart time series from a Ta4j time series and an indicator.
     * @param tickSeries the ta4j time series
     * @param indicator the indicator
     * @param name the name of the chart time series
     * @return the JFreeChart time series
     */
    private static org.jfree.data.time.TimeSeries buildChartTimeSeries(TimeSeries tickSeries, Indicator<Decimal> indicator, String name) {
        org.jfree.data.time.TimeSeries chartTimeSeries = new org.jfree.data.time.TimeSeries(name);
        for (int i = 0; i < tickSeries.getTickCount(); i++) {
            Tick tick = tickSeries.getTick(i);
            chartTimeSeries.add(new Minute(tick.getEndTime().toDate()), indicator.getValue(i).toDouble());
        }
        return chartTimeSeries;
    }

    /**
     * Adds the cash flow axis to the plot.
     * @param plot the plot
     * @param dataset the cash flow dataset
     */
    private static void addCashFlowAxis(XYPlot plot, TimeSeriesCollection dataset) {
        final NumberAxis cashAxis = new NumberAxis("Cash Flow Ratio");
        cashAxis.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, cashAxis);
        plot.setDataset(1, dataset);
        plot.mapDatasetToRangeAxis(1, 1);
        final StandardXYItemRenderer cashFlowRenderer = new StandardXYItemRenderer();
        cashFlowRenderer.setSeriesPaint(0, Color.blue);
        plot.setRenderer(1, cashFlowRenderer);
    }
    /**
     * Displays a chart in a frame.
     * @param chart the chart to be displayed
     */
    private static void displayChart(JFreeChart chart) {
        // Chart panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(1024, 400));
        frame = new ApplicationFrame("Ta4j example - Cash flow to chart");
        frame.setContentPane(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void goDie(){
    	frame.dispose();
    }

    public static void build(TimeSeries series,Strategy strategy) {


        // Running the strategy
        TradingRecord tradingRecord = series.run(strategy);
        // Getting the cash flow of the resulting trades
        CashFlow cashFlow = new CashFlow(series, tradingRecord);
        MedianPriceIndicator price = new MedianPriceIndicator(series);
        /**
         * Building chart datasets
         */
        TimeSeriesCollection datasetAxis1 = new TimeSeriesCollection();
        datasetAxis1.addSeries(buildChartTimeSeries(series, new ClosePriceIndicator(series), "Bitstamp Bitcoin (BTC)"));
        TimeSeriesCollection datasetAxis2 = new TimeSeriesCollection();
        datasetAxis2.addSeries(buildChartTimeSeries(series, cashFlow, "Cash Flow"));
        TimeSeriesCollection datasetAxis3 = new TimeSeriesCollection();
        datasetAxis3.addSeries(buildChartTimeSeries(series, new FisherIndicator(price, 8), "Fisher"));
        TimeSeriesCollection datasetAxis4 = new TimeSeriesCollection();
        datasetAxis4.addSeries(buildChartTimeSeries(series, new FisherIndicator(price, 10), "Fisher2"));

        /**
         * Creating the chart
         */
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Bitstamp BTC", // title
                "Date", // x-axis label
                "Price", // y-axis label
                datasetAxis1, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
                );
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));

        /**
         * Adding the cash flow axis (on the right)
         */
        addCashFlowAxis(plot, datasetAxis2);
        displayChart(chart);
        

    }
}