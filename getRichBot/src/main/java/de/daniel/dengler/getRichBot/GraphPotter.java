package de.daniel.dengler.getRichBot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TimeSeries;

public class GraphPotter extends JPanel {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeSeries serie;
	private int myWidth;
	private int myHeight;
	private Indicator[] indies;

	
	
	public GraphPotter(TimeSeries serie,int width, int height, Indicator<Decimal>... indies){
	this.serie = serie;
	this.myWidth = width;
	this.myHeight = height;
	this.indies = indies;
	setPreferredSize(new Dimension(myWidth, myHeight));
	
	
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < indies.length; i++) {
			Indicator<Decimal> ind = indies[1];
			g.setColor(Color.BLACK);
			for (int tickNo = 0; tickNo <= serie.getEnd(); tickNo++) {
				
				
			}
		}
	}
}
