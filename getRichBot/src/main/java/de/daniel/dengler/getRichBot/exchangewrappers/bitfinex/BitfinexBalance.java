package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import de.daniel.dengler.getRichBot.Balance;
import de.daniel.dengler.getRichBot.Watcher;

public class BitfinexBalance implements Balance{
	private Watcher w;
	public BitfinexBalance(Watcher w) {
		this.w = w;

	}
	String type;
	String currency;
	double amount;
	double available;
	public double getMaxSellCoBalance() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getMaxSellBalance() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void sellCoBa(double sellPrice, double coba) {
		// TODO Auto-generated method stub
		
	}
	public void buyCoBa(double buyPrice, double ba) {
		// TODO Auto-generated method stub
		
	}

}
