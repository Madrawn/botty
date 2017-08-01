package de.daniel.dengler.getRichBot;

public interface Balance {

	double getMaxSellCoBalance();

	double getMaxSellBalance();

	void sellCoBa(double sellPrice, double coba);

	void buyCoBa(double buyPrice, double ba);
	
	
}
