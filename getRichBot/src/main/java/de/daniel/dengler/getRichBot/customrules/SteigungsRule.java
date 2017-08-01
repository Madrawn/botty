package de.daniel.dengler.getRichBot.customrules;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.trading.rules.AbstractRule;

public class SteigungsRule extends AbstractRule {
	Indicator<Decimal> indicator;

	public SteigungsRule(Indicator<Decimal> ind){
		this.indicator = ind;
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {

		Decimal rightNow, before, difference;
		rightNow = indicator.getValue(index);
		before = indicator.getValue(index-1);
		
		difference = rightNow.minus(before);
		
		if(difference.isGreaterThanOrEqual(Decimal.ZERO)){
			return true;
		}
		
		return false;
	}
	


}
