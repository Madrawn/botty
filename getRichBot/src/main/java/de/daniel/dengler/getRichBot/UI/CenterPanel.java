package de.daniel.dengler.getRichBot.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import de.daniel.dengler.getRichBot.Watcher;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class CenterPanel extends JPanel {
	private JTextField textField;
	public BigWatcherPanel bigPanel;
	private JPanel contentPanel;

	/**
	 * Create the panel.
	 */
	public CenterPanel() {

		setPreferredSize(new Dimension(800, 800));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		JPanel ButtonPanel = new JPanel();
		add(ButtonPanel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Show Graph");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(bigPanel == null){
					contentPanel.getGraphics().drawString("No Watcher selected!", 50, 50); 
					return;
				}
				AllmightyController.mouseGraphClicked(bigPanel.getWatcher().getLeftListItem());
			}
		});
		ButtonPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("List Ticks");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bigPanel == null){
					contentPanel.getGraphics().drawString("No Watcher selected!", 50, 50); 
					return;
				}
				contentPanel.removeAll();
				contentPanel.setLayout(new BorderLayout());
				Watcher w = bigPanel.getWatcher();
				TimeSeries t = w.getTimeSeries();
				JTextArea jt = new JTextArea();
				for (int i = 0; i < t.getTickCount(); i++) {
					Tick tick = t.getTick(i);
					jt.append(tick.toString()+"\n");
				}
				ScrollPane pane = new ScrollPane();
				pane.add(jt);
				
				contentPanel.add(pane,BorderLayout.CENTER);
				validate();
				
			}
		});
		ButtonPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Show Balance/Positions");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bigPanel == null){
					contentPanel.getGraphics().drawString("No Watcher selected!", 50, 50); 
					return;
				}
				
				AllmightyController.showBalanceClicked(contentPanel, bigPanel.getWatcher());
			}
		});
		ButtonPanel.add(btnNewButton_2);
		
		textField = new JTextField();
		ButtonPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnAddBot = new JButton("Add Bot");
		btnAddBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllmightyController.addBotClicked(bigPanel.getWatcher());
			}
		});
		ButtonPanel.add(btnAddBot);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(contentPanel, BorderLayout.CENTER);

	}

	public void addBigPanel(BigWatcherPanel bw) {
		this.bigPanel = bw;
		contentPanel.removeAll();
		contentPanel.add(bw,null);
		bw.setVisible(true);
		contentPanel.setVisible(true);
	}

}
