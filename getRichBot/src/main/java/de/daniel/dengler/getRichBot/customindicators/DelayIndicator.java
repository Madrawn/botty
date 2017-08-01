package de.daniel.dengler.getRichBot.customindicators;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class DelayIndicator extends CachedIndicator<Decimal> {

	Indicator<Decimal> indicator;
	int barsToSkip;
	
	public DelayIndicator(Indicator<Decimal> indicator) {
		this(indicator, 1);
	}

	public DelayIndicator(Indicator<Decimal> indicator2, int i) {
		super(indicator2);
		this.indicator = indicator2;
		this.barsToSkip = i;
	}

	@Override
	protected Decimal calculate(int index) {
		// TODO Auto-generated method stub
		if(index == 0)
			return indicator.getValue(0);
		return indicator.getValue(index-barsToSkip);
	}

}
