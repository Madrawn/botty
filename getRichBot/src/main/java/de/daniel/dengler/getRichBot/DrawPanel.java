package de.daniel.dengler.getRichBot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import de.daniel.dengler.getRichBot.DataOverlord.State;

public class DrawPanel extends JPanel {

	public long diff= 0;
	/**
	 * Create the panel.
	 */
	public DrawPanel() {

	}
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("v.0021a Trading "+ DataOverlord.currencyPair, 0, 10);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        if(DataOverlord.checkLiveStrategy() == State.buy){
        	g.setColor(Color.RED);
        	g.drawString("BUY!", 50+new Random().nextInt(10), 50+new Random().nextInt(10));
        	g.drawString("Buy below:"+DataOverlord.series.getLastTick().getClosePrice(), 150, 150);
        	this.requestFocus();
        }else if (DataOverlord.checkLiveStrategy() == State.sell){
        	
        	g.setColor(Color.green);
        	g.drawString("SELL!", 50+new Random().nextInt(10), 50+new Random().nextInt(10));
        	g.drawString("Sell above:"+ DataOverlord.series.getLastTick().getClosePrice(), 150, 150);
        	this.requestFocus();
        }else if (DataOverlord.checkLiveStrategy() == State.wait){
        	g.setColor(Color.gray);
        	g.drawString("wait...", 50, 50);
        	
        }
        g.setFont((new Font("Arial", Font.BOLD, 13)));
        g.setColor(Color.RED);
        g.drawString("Update in: "+diff, 0, 250);
        
    }
	
}
