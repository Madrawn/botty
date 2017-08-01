package de.daniel.dengler.getRichBot;

import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;

public class ShortableStrategy extends Strategy {

	protected Rule exitRule;
	protected Rule entryRule;

	public ShortableStrategy(Rule entryRule, Rule exitRule) {
		super(entryRule, exitRule;
		this.entryRule = entryRule;
		this.exitRule = exitRule;
	}

}
