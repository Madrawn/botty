package de.daniel.dengler.getRichBot;

import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;

public class ReversableStrategy extends Strategy {

	protected Rule exitRule;
	protected Rule entryRule;

	public ReversableStrategy(Rule entryRule, Rule exitRule) {
		super(entryRule, exitRule);
		this.entryRule = entryRule;
		this.exitRule = exitRule;
	}

	public Strategy getReversedStrategy(){
		return new Strategy(exitRule, entryRule);
	}
}
