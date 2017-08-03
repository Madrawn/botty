package de.daniel.dengler.getRichBot;


public class FakeBalance implements Balance {

	
	private double balance;
	private double coBalance;
	private double collectedFee = 0;
	double feePercent = 0.002;
	double partSold = 0.5;
	
	public FakeBalance(double startBalance, double startCoBalance) {
		this.balance = startBalance;
		this.coBalance = startCoBalance;
	}

	public void buyCoBa(double buyPrice, double amount) {
		
		double soldBalance = amount; 
		double boughtCoB = soldBalance/buyPrice;
		coBalance += boughtCoB;
		balance -= soldBalance;
		collectedFee+= soldBalance * feePercent ;
		Logger.debugLog(String.format("Bought %s for %s with %s having %s left", boughtCoB, buyPrice, balance, balance ));
		actionStatus(buyPrice, "buy");
	}

	public void sellCoBa(double sellPrice, double amount) {
		//sell twice? 50%?
		double soldCoB = amount;
		double gain = soldCoB * sellPrice;
		balance += gain;
		coBalance -= soldCoB;
		collectedFee+= gain * feePercent ;
		Logger.debugLog(String.format("Sold %s for %s gaining %s having %s left",soldCoB, sellPrice, gain, balance ));
		actionStatus(sellPrice, "sell");
	}
	
	public void actionStatus(double price, String action){
		Logger.normalLog(String.format("\nAction: %s\nPrice: %s\nBalance: %s\nCoBalance: %s\nFee: %s", action, price, balance, coBalance, collectedFee));
		
	}
	
	public double getMaxSellBalance(){
		
		return Math.max(0, balance);
	}
	public double getMaxSellCoBalance(){
		
		return Math.max(0, coBalance);
	}

}
