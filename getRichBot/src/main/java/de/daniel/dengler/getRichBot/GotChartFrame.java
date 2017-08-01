package de.daniel.dengler.getRichBot;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GotChartFrame extends JFrame {

	private JPanel contentPane;
	private JFrame me = this;

	/**
	 * Create the frame.
	 */
	public GotChartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 486, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblYouHaveA = new JLabel("You have a normal chart.");
		GridBagConstraints gbc_lblYouHaveA = new GridBagConstraints();
		gbc_lblYouHaveA.insets = new Insets(0, 0, 5, 5);
		gbc_lblYouHaveA.gridx = 0;
		gbc_lblYouHaveA.gridy = 0;
		contentPane.add(lblYouHaveA, gbc_lblYouHaveA);

		JLabel lblDatapoints = new JLabel("DataPoints:");
		GridBagConstraints gbc_lblDatapoints = new GridBagConstraints();
		gbc_lblDatapoints.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatapoints.gridx = 0;
		gbc_lblDatapoints.gridy = 1;
		contentPane.add(lblDatapoints, gbc_lblDatapoints);

		JLabel lblDpoints = new JLabel("" + DataOverlord.chartPoints.length);
		GridBagConstraints gbc_lblDpoints = new GridBagConstraints();
		gbc_lblDpoints.insets = new Insets(0, 0, 5, 5);
		gbc_lblDpoints.gridx = 1;
		gbc_lblDpoints.gridy = 1;
		contentPane.add(lblDpoints, gbc_lblDpoints);

		JLabel lblFrom = new JLabel("From:");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrom.gridx = 0;
		gbc_lblFrom.gridy = 2;
		contentPane.add(lblFrom, gbc_lblFrom);

		JLabel lblNumfrom = new JLabel("" + new Date(1000 * DataOverlord.start));
		GridBagConstraints gbc_lblNumfrom = new GridBagConstraints();
		gbc_lblNumfrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumfrom.gridx = 1;
		gbc_lblNumfrom.gridy = 2;
		contentPane.add(lblNumfrom, gbc_lblNumfrom);

		JLabel lblTo = new JLabel("To:");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 2;
		gbc_lblTo.gridy = 2;
		contentPane.add(lblTo, gbc_lblTo);

		JLabel lblNumto = new JLabel("" + new Date(1000 * DataOverlord.end));
		GridBagConstraints gbc_lblNumto = new GridBagConstraints();
		gbc_lblNumto.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumto.gridx = 3;
		gbc_lblNumto.gridy = 2;
		contentPane.add(lblNumto, gbc_lblNumto);

		JLabel lblPeriodLength = new JLabel("Period Length:");
		GridBagConstraints gbc_lblPeriodLength = new GridBagConstraints();
		gbc_lblPeriodLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodLength.gridx = 0;
		gbc_lblPeriodLength.gridy = 3;
		contentPane.add(lblPeriodLength, gbc_lblPeriodLength);

		JLabel lblPlength = new JLabel("" + DataOverlord.period);
		GridBagConstraints gbc_lblPlength = new GridBagConstraints();
		gbc_lblPlength.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlength.gridx = 1;
		gbc_lblPlength.gridy = 3;
		contentPane.add(lblPlength, gbc_lblPlength);

		JLabel lblCurrencyPair = new JLabel("Currency Pair:");
		GridBagConstraints gbc_lblCurrencyPair = new GridBagConstraints();
		gbc_lblCurrencyPair.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrencyPair.gridx = 0;
		gbc_lblCurrencyPair.gridy = 4;
		contentPane.add(lblCurrencyPair, gbc_lblCurrencyPair);

		JLabel lblCpari = new JLabel("" + DataOverlord.currencyPair);
		GridBagConstraints gbc_lblCpari = new GridBagConstraints();
		gbc_lblCpari.insets = new Insets(0, 0, 5, 5);
		gbc_lblCpari.gridx = 1;
		gbc_lblCpari.gridy = 4;
		contentPane.add(lblCpari, gbc_lblCpari);

		JButton btnGoToBacktrading = new JButton("Backtrading");
		btnGoToBacktrading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BackTradingFrame frame = new BackTradingFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				me.dispose();
			}
		});
		GridBagConstraints gbc_btnGoToBacktrading = new GridBagConstraints();
		gbc_btnGoToBacktrading.insets = new Insets(0, 0, 0, 5);
		gbc_btnGoToBacktrading.gridx = 0;
		gbc_btnGoToBacktrading.gridy = 9;
		contentPane.add(btnGoToBacktrading, gbc_btnGoToBacktrading);

		JButton btnGetNewChart = new JButton("Get new chart");
		btnGetNewChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MainWindow window = new MainWindow();
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				me.dispose();
			}
		});
		GridBagConstraints gbc_btnGetNewChart = new GridBagConstraints();
		gbc_btnGetNewChart.gridx = 3;
		gbc_btnGetNewChart.gridy = 9;
		contentPane.add(btnGetNewChart, gbc_btnGetNewChart);
	}

}
