package de.daniel.dengler.getRichBot.UI;

import javax.swing.JPanel;
import javax.swing.JInternalFrame;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LeftList extends JPanel {


	private LeftList me = this;
	private JPanel itemPanel;
	
	/**
	 * Create the panel.
	 */
	public LeftList() {
		
		setPreferredSize(new Dimension(180, 800));
		setMinimumSize(new Dimension(150, 10));
		setMaximumSize(new Dimension(200, 32767));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel mainPanel = new JPanel();
		scrollPane.setViewportView(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		itemPanel = new JPanel();
		mainPanel.add(itemPanel);
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new LeftListItem();
		itemPanel.add(panel_1);
		
		JPanel panel = new LeftListItem();
		itemPanel.add(panel);
		
		JButton btnAddWatch = new JButton("Add Watch");
		btnAddWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AllmightyController.addWatchClicked(arg0, me);
			}
		});
		btnAddWatch.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(btnAddWatch);

	}

	public void addNewItem(LeftListItem newItem) {
		itemPanel.add(newItem);
		validate();
	}
}
