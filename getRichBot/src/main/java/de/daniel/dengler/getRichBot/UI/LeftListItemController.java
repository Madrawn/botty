package de.daniel.dengler.getRichBot.UI;

import de.daniel.dengler.getRichBot.Watcher;

public class LeftListItemController {

	Watcher myWatcher;
	LeftListItem myItem;

	public LeftListItemController(LeftListItem item) {
		myItem = item;
		myItem.setController(this);
	}

	public void setDailyHigh(double dailyHigh) {
		myItem.lblHi.setText("Hi: " + dailyHigh);

	}

	public void setDailyLow(double dailyLow) {
		myItem.lblLo.setText("Lo: " + dailyLow);
		
	}

	public void setCurrencyPair(String pairAsString) {
		myItem.lblCurrencypair.setText(pairAsString);

	}

	public void setExchange(String exchangeName) {
		myItem.lblExchange.setText(exchangeName);

	}

	public void setCurPrice(double currentPrice) {
		myItem.lblCur.setText("Cur: " + currentPrice);

	}

	public void remove() {
		myWatcher.interrupt();
	}

	public void setWatcher(Watcher newWatcher) {
		this.myWatcher = newWatcher;
	}

	public LeftListItem getLeftListItem() {
		return myItem;
	}

}
