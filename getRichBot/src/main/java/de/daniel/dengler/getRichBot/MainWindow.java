package de.daniel.dengler.getRichBot;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;

public class MainWindow {

	public static String gainSpinner;
	JFrame frame;
	private JTextField txtCpair;
	private DataOverlord dover;
	private MainWindow me = this;
	private JSpinner spinner;
	private JComboBox comboBoxPeriodLength;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 289, 186);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnGetChart = new JButton("Get Chart");
		btnGetChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean success = DataOverlord.collectChart(me); 
				if(success){
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
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");
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
					
					frame.dispose();
				}
			}
		});
		panel.add(btnGetChart);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNumperiods = new JLabel("NumPeriods:");
		panel_1.add(lblNumperiods);
		
		spinner = new JSpinner();
		spinner.setValue(new Integer(400));
		panel_1.add(spinner);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		JLabel lblNewLabel = new JLabel("Period Length:");
		panel_1.add(lblNewLabel);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue_1);
		
		comboBoxPeriodLength = new JComboBox();
		comboBoxPeriodLength.setModel(new DefaultComboBoxModel(new String[] {"300", "900", "1800", "7200", "14400", "86400"}));
		panel_1.add(comboBoxPeriodLength);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		
		JLabel lblCurrencyPair = new JLabel("Currency Pair:");
		panel_1.add(lblCurrencyPair);
		
		txtCpair = new JTextField();
		txtCpair.setText("cPair");
		panel_1.add(txtCpair);
		txtCpair.setColumns(10);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
	}

	public String getCurrencyPair() {
		
		return txtCpair.getText();
	}

	public int getNumPeriods() {
		return (Integer) spinner.getValue();
	}

	public int getPeriod() {
		String box = (String) comboBoxPeriodLength.getSelectedItem();
		
		Integer test = Integer.valueOf(box);
		int period =  test.intValue();
		return period;
	}

}
