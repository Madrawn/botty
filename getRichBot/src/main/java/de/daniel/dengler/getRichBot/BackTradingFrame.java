package de.daniel.dengler.getRichBot;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import de.daniel.dengler.getRichBot.BackTradingFrame.Strategies;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.JTextField;

public class BackTradingFrame extends JFrame {

	enum Indicators{
		MACD,
		
	}
	
	enum Strategies{
		findBest, MovingMomentumStrategy,
		NilsStrategy, DanielStrategy, CCIStrategy, RSIStrategy, findBestFish, FishStrategy
	}

	public static String gainSpinner;
	
	private JPanel contentPane;
	public JLabel lblRes_1;
	public JLabel lblRes;
	private JFrame me = this;
	public JComboBox comboBox;
	public JTextField txtAsdasd;
	private JTextField txtChicken;
	
	


	/**
	 * Create the frame.
	 */
	public BackTradingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSelectAStrategy = new JLabel("Select a strategy");
		GridBagConstraints gbc_lblSelectAStrategy = new GridBagConstraints();
		gbc_lblSelectAStrategy.gridwidth = 4;
		gbc_lblSelectAStrategy.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectAStrategy.gridx = 0;
		gbc_lblSelectAStrategy.gridy = 0;
		contentPane.add(lblSelectAStrategy, gbc_lblSelectAStrategy);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(Strategies.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBox, gbc_comboBox);
		
		JButton btnTestOverTimeseries = new JButton("Test over Timeseries");
		btnTestOverTimeseries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataOverlord.minGain = txtAsdasd.getText();
				DataOverlord.chickenOut = txtChicken.getText();
				Strategies selectedItem = (Strategies) comboBox.getSelectedItem();
				TradingBot.testStrategy(selectedItem);
				lblRes.setText(DataOverlord.res1);
				lblRes_1.setText(DataOverlord.res2);
				
				
			}
		});
		
		JLabel lblMingain = new JLabel("minGain");
		GridBagConstraints gbc_lblMingain = new GridBagConstraints();
		gbc_lblMingain.anchor = GridBagConstraints.EAST;
		gbc_lblMingain.insets = new Insets(0, 0, 5, 5);
		gbc_lblMingain.gridx = 8;
		gbc_lblMingain.gridy = 1;
		contentPane.add(lblMingain, gbc_lblMingain);
		
		txtAsdasd = new JTextField();
		txtAsdasd.setText("0.25");
		GridBagConstraints gbc_txtAsdasd = new GridBagConstraints();
		gbc_txtAsdasd.gridwidth = 3;
		gbc_txtAsdasd.insets = new Insets(0, 0, 5, 0);
		gbc_txtAsdasd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAsdasd.gridx = 9;
		gbc_txtAsdasd.gridy = 1;
		contentPane.add(txtAsdasd, gbc_txtAsdasd);
		txtAsdasd.setColumns(10);
		
		JLabel lblChickenout = new JLabel("chickenOut");
		GridBagConstraints gbc_lblChickenout = new GridBagConstraints();
		gbc_lblChickenout.insets = new Insets(0, 0, 5, 5);
		gbc_lblChickenout.gridx = 8;
		gbc_lblChickenout.gridy = 2;
		contentPane.add(lblChickenout, gbc_lblChickenout);
		
		txtChicken = new JTextField();
		txtChicken.setText("5");
		GridBagConstraints gbc_txtChicken = new GridBagConstraints();
		gbc_txtChicken.gridwidth = 3;
		gbc_txtChicken.insets = new Insets(0, 0, 5, 5);
		gbc_txtChicken.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtChicken.gridx = 9;
		gbc_txtChicken.gridy = 2;
		contentPane.add(txtChicken, gbc_txtChicken);
		txtChicken.setColumns(10);
		GridBagConstraints gbc_btnTestOverTimeseries = new GridBagConstraints();
		gbc_btnTestOverTimeseries.gridwidth = 4;
		gbc_btnTestOverTimeseries.insets = new Insets(0, 0, 5, 5);
		gbc_btnTestOverTimeseries.gridx = 0;
		gbc_btnTestOverTimeseries.gridy = 3;
		contentPane.add(btnTestOverTimeseries, gbc_btnTestOverTimeseries);
		
		JButton btnGetLive = new JButton("Get live");
		btnGetLive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(DataOverlord.strat == null){
					return;
				}
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							LiveRobot frame = new LiveRobot();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				CashFlowToChart.goDie();
				me.dispose();
			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							GotChartFrame frame = new GotChartFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				me.dispose();
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBack.gridx = 11;
		gbc_btnBack.gridy = 5;
		contentPane.add(btnBack, gbc_btnBack);
		GridBagConstraints gbc_btnGetLive = new GridBagConstraints();
		gbc_btnGetLive.insets = new Insets(0, 0, 5, 0);
		gbc_btnGetLive.gridx = 11;
		gbc_btnGetLive.gridy = 6;
		contentPane.add(btnGetLive, gbc_btnGetLive);
		
		lblRes_1 = new JLabel("res2");
		GridBagConstraints gbc_lblRes_1 = new GridBagConstraints();
		gbc_lblRes_1.gridwidth = 9;
		gbc_lblRes_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRes_1.gridx = 1;
		gbc_lblRes_1.gridy = 7;
		contentPane.add(lblRes_1, gbc_lblRes_1);
		
		JLabel lblResult = new JLabel("Result:");
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 0, 5);
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 8;
		contentPane.add(lblResult, gbc_lblResult);
		
		lblRes = new JLabel("res");
		GridBagConstraints gbc_lblRes = new GridBagConstraints();
		gbc_lblRes.gridwidth = 9;
		gbc_lblRes.insets = new Insets(0, 0, 0, 5);
		gbc_lblRes.gridx = 1;
		gbc_lblRes.gridy = 8;
		contentPane.add(lblRes, gbc_lblRes);
	}

}
