package de.daniel.dengler.getRichBot.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePairs;
import de.daniel.dengler.getRichBot.enums.BitfinexPossiblePeriodLengths;
import de.daniel.dengler.getRichBot.enums.PossibleExchanges;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexWrapperConnector;
import de.daniel.dengler.getRichBot.exchangewrappers.bitfinex.BitfinexWrapperSettings;

import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddWatchDialogTwo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<Enum> pairComboBox;
	private JComboBox<Enum> periodLengthComboBox;
	private JSpinner numPeriodSpinner;
	private JCheckBox chckbxUpdateChart;
	private IExchangeWrapper exchangeWrapper;

	

	/**
	 * Create the dialog.
	 * @param possibleExchange 
	 */
	@SuppressWarnings("unchecked")
	public AddWatchDialogTwo(PossibleExchanges possibleExchange) {
		
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Step Two");
		setBounds(100, 100, 348, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			Box verticalBox = Box.createVerticalBox();
			contentPanel.add(verticalBox);
			{
				JLabel lblChooseACurrency = new JLabel("Choose a currency pair");
				verticalBox.add(lblChooseACurrency);
			}
			{
				pairComboBox = new JComboBox<Enum>();
				verticalBox.add(pairComboBox);
			}
		}
		{
			Box verticalBox_1 = Box.createVerticalBox();
			contentPanel.add(verticalBox_1);
			{
				JLabel lblChooseAPeriod = new JLabel("Choose a period length");
				verticalBox_1.add(lblChooseAPeriod);
			}
			{
				periodLengthComboBox = new JComboBox<Enum>();
				verticalBox_1.add(periodLengthComboBox);
			}
		}
		{
			Box verticalBox = Box.createVerticalBox();
			contentPanel.add(verticalBox);
			{
				JLabel lblAmountOfPeriods = new JLabel("Amount of periods");
				verticalBox.add(lblAmountOfPeriods);
			}
			{
				numPeriodSpinner = new JSpinner();
				numPeriodSpinner.setModel(new SpinnerNumberModel(52, 1, 400, 1));
				verticalBox.add(numPeriodSpinner);
			}
		}
		{
			Box verticalBox = Box.createVerticalBox();
			contentPanel.add(verticalBox);
			{
				JLabel lblLiveUpdate = new JLabel("Live update");
				verticalBox.add(lblLiveUpdate);
			}
			{
				chckbxUpdateChart = new JCheckBox("Update chart");
				verticalBox.add(chckbxUpdateChart);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Enum selectedItem = (Enum)pairComboBox.getSelectedItem();
						exchangeWrapper.getSettings().setCurrencyPair(selectedItem);
						selectedItem = (Enum) periodLengthComboBox.getSelectedItem();
						exchangeWrapper.getSettings().setPeriodLength(selectedItem);
						exchangeWrapper.getSettings().setNumPeriods((Integer)numPeriodSpinner.getValue());
						exchangeWrapper.getSettings().setIsUpdating(chckbxUpdateChart.isSelected());
						AllmightyController.addWatchDialogsDone(exchangeWrapper);
						
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		switch (possibleExchange) {
		case BITFINEX:
			pairComboBox.setModel(new DefaultComboBoxModel<Enum>(BitfinexPossiblePairs.values()));
			periodLengthComboBox.setModel(new DefaultComboBoxModel<Enum>(BitfinexPossiblePeriodLengths.values()));
			exchangeWrapper = new BitfinexExchangeWrapper(new BitfinexWrapperSettings(), new BitfinexWrapperConnector());
			break;

		default:
			break;
		}
		
		
	}

}
