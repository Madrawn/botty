package de.daniel.dengler.getRichBot;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Date;

public class LiveRobot extends JFrame {

	private JPanel contentPane;
	public static Runnable update = new Runnable() {
		
		private boolean cont;

		@Override
		public void run() {
			DataOverlord.live = true;
			Date now = new Date();
			Date then = new Date(now.getTime()+ 15 * 1000 + 60 * 1000 * (5-(now.getMinutes()%5)));
			then.setSeconds(15);
			while(DataOverlord.live){
				now = new Date();
				if(now.after(then)){
					then = new Date(now.getTime()+ 15 * 1000 + 60 * 1000 * (5-(now.getMinutes()%5)));
					then.setSeconds(15);
					DataOverlord.updateSeries();
				}
				long dif = then.getTime() - now.getTime();
				panel.diff = dif;
				panel.repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	};
	public static DrawPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LiveRobot frame = new LiveRobot();
					frame.setTitle("Pair"+DataOverlord.currencyPair);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LiveRobot() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		panel = new DrawPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		Graphics g = panel.getGraphics();
		new Thread(LiveRobot.update).start();
		
	}
	


}
