package de.daniel.dengler.getRichBot.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

import de.daniel.dengler.getRichBot.Helper;
import de.daniel.dengler.getRichBot.StrategyBuilder;
import de.daniel.dengler.getRichBot.Watcher;
import de.daniel.dengler.getRichBot.enums.StrategyType;
import eu.verdelhan.ta4j.AnalysisCriterion;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.analysis.CashFlow;
import eu.verdelhan.ta4j.analysis.criteria.TotalProfitCriterion;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.simple.MedianPriceIndicator;

public class BigWatcherPanel extends JPanel {

	private Watcher watcher;
	private Box mainSettingsBox;

	public BigWatcherPanel(Watcher w) {

		this.watcher = w;
		TimeSeries series = w.getTimeSeries();
		MedianPriceIndicator price = new MedianPriceIndicator(series);
		/**
		 * Building chart datasets
		 */
		TimeSeriesCollection datasetAxis1 = new TimeSeriesCollection();
		datasetAxis1.addSeries(buildChartTimeSeries(series,
				new ClosePriceIndicator(series), "Bitstamp Bitcoin (BTC)"));

		/**
		 * Creating the chart
		 */
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Bitstamp BTC", // title
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

		if (watcher.getLongStrat() != null) {
			// Running the strategy
			TradingRecord tradingRecord = series.run(watcher.getLongStrat());
			// Getting the cash flow of the resulting trades
			CashFlow cashFlow = new CashFlow(series, tradingRecord);
			TimeSeriesCollection datasetAxis2 = new TimeSeriesCollection();
			datasetAxis2.addSeries(buildChartTimeSeries(series, cashFlow,
					"Cash Flow"));
		}

		ChartPanel panel = new ChartPanel(chart);
		panel.setFillZoomRectangle(true);
		panel.setMouseWheelEnabled(true);
		// panel.setPreferredSize(new Dimension(1024, 400));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panel);

		mainSettingsBox = Box.createVerticalBox();
		add(mainSettingsBox);
		
		
		Box chooseStratBox = Box.createHorizontalBox();
		JLabel lblChooseStrat = new JLabel("Choose a strategy!");
		chooseStratBox.add(lblChooseStrat);
		JComboBox<StrategyType> comboStratBox = new JComboBox<StrategyType>();
		comboStratBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED) {
					updateSettingsBox((StrategyType) e.getItem());
				}
			}
		});
		comboStratBox.setModel(new DefaultComboBoxModel<StrategyType>(
				StrategyType.values()));
		chooseStratBox.add(comboStratBox);

		updateSettingsBox((StrategyType) comboStratBox.getSelectedItem());
		add(chooseStratBox);
		this.setVisible(true);
		validate();

	}

	protected void updateSettingsBox(StrategyType item) {

		mainSettingsBox.removeAll();
		
		JLabel lblNotImplemented = new JLabel("Not yet implemented");

		switch (item) {
		case Auto_Ema:
			Box hBox = Box.createHorizontalBox();
			JLabel lblDescription = new JLabel(
					"Settings will be auto generated");
			JButton btnGenerate = new JButton("Generate");
			btnGenerate.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Map<Strategy, String> strategies = StrategyBuilder
							.buildEMAStrategiesMap(watcher.getTimeSeries());
					Strategy best = calcBest(strategies);
					watcher.setLongStrat(best);
					AllmightyController.mouseGraphClicked(watcher
							.getLeftListItem());
				}
			});
			
			hBox.add(lblDescription);
			hBox.add(btnGenerate);
			mainSettingsBox.add(hBox);
			validate();
			break;
		case Auto_Fisher:
			Box hBox1 = Box.createHorizontalBox();
			JLabel lblDescription1 = new JLabel(
					"Settings will be auto generated");
			JButton btnGenerate1 = new JButton("Generate");
			btnGenerate1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Map<Strategy, String> strategies = StrategyBuilder
							.buildEMAStrategiesMap(watcher.getTimeSeries());
					Strategy best = calcBest(strategies);
					watcher.setLongStrat(best);
					AllmightyController.mouseGraphClicked(watcher
							.getLeftListItem());
				}
			});
			hBox1.add(lblDescription1);
			hBox1.add(btnGenerate1);
			mainSettingsBox.add(hBox1);

			break;
		case Ema:
			mainSettingsBox.add(lblNotImplemented);
			break;
		case Fisher:
			mainSettingsBox.add(lblNotImplemented);

			break;

		default:
			break;
		}
		
	}

	private org.jfree.data.time.TimeSeries buildChartTimeSeries(
			TimeSeries series, Indicator<Decimal> indicator, String string) {
		return Helper.buildChartTimeSeries(series, indicator, string);
	}

	public Watcher getWatcher() {
		// TODO Auto-generated method stub
		return this.watcher;
	}

	/**
	 * @param strategies
	 * @return
	 */
	protected Strategy calcBest(Map<Strategy, String> strategies) {
		Strategy bestS = null;
		double bestP = 0;
		String bestN = null;

		// Building the map of strategies
		// The analysis criterion
		AnalysisCriterion profitCriterion = new TotalProfitCriterion();

		TimeSeries series = watcher.getTimeSeries();

		for (Map.Entry<Strategy, String> entry : strategies.entrySet()) {
			Strategy strategy = entry.getKey();
			String name = entry.getValue();
			// For each strategy...
			TradingRecord tradingRecord = series.run(strategy);
			double profit = profitCriterion.calculate(series, tradingRecord);
			if (profit > bestP) {
				bestP = profit;
				bestS = strategy;
				bestN = name;
			}
			System.out.println("\tProfit for " + name + ": " + profit);
		}
		TradingRecord tradingRecord = series.run(bestS);
		double profit = profitCriterion.calculate(series, tradingRecord);

		System.out.println("\tBEST Profit for " + bestN + ": " + profit);
		return bestS;
	}

}
