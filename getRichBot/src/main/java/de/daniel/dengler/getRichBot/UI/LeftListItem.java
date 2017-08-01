package de.daniel.dengler.getRichBot.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class LeftListItem extends JPanel {

	protected JLabel lblExchange;
	protected JLabel lblCurrencypair;
	protected JLabel lblHi;
	protected JLabel lblLo;
	private LeftListItem me = this;
	LeftListItemController myController;
	public JLabel lblCur;

	/**
	 * Create the panel.
	 */
	public LeftListItem() {
		this.myController = myController;
		setPreferredSize(new Dimension(150, 80));

		setMinimumSize(new Dimension(150, 80));
		setMaximumSize(new Dimension(150, 80));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPanel mes = (JPanel) arg0.getSource();
				mes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null,
						null, null));
				
				AllmightyController.mouseGraphClicked(me);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel me = (JPanel) arg0.getSource();
				me.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
						null, null));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JPanel me = (JPanel) e.getSource();
				me.setBorder(new BevelBorder(BevelBorder.RAISED, null, null,
						null, null));
			}
		});
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(null);

		lblCurrencypair = new JLabel("CurrencyPair");
		lblCurrencypair.setBounds(10, 11, 69, 14);
		add(lblCurrencypair);

		lblHi = new JLabel("Hi: ");
		lblHi.setForeground(new Color(50, 205, 50));
		lblHi.setBounds(75, 11, 124, 14);
		add(lblHi);

		lblLo = new JLabel("Lo: ");
		lblLo.setForeground(new Color(255, 0, 0));
		lblLo.setBounds(75, 25, 124, 14);
		add(lblLo);

		JLabel lblNewLabel = new JLabel("X");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to remove?", "Remove?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					JLabel j = (JLabel) e.getSource();
					Container parent = j.getParent().getParent();
					parent.remove(j.getParent());
					parent.revalidate();
					myController.remove();
				}
			}
		});
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(131, 10, 19, 14);
		add(lblNewLabel);
		
		lblExchange = new JLabel("Exchange");
		lblExchange.setBounds(10, 55, 96, 14);
		add(lblExchange);
		
		lblCur = new JLabel("Cur: ");
		lblCur.setBounds(75, 36, 104, 14);
		add(lblCur);

	}

	public void setDailyHigh(double dailyHigh) {
		// TODO Auto-generated method stub
		
	}

	public void setDailyLow(double dailyLow) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrencyPair(String currencyPair) {
		// TODO Auto-generated method stub
		
	}

	public void setExchange(String exchangeName) {
		// TODO Auto-generated method stub
		
	}

	public void setController(LeftListItemController leftListItemController) {
		myController = leftListItemController;
	}
}
