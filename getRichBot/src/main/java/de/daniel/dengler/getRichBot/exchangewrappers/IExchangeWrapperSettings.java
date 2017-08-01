package de.daniel.dengler.getRichBot.exchangewrappers;

import de.daniel.dengler.getRichBot.enums.PossibleExchanges;


public interface IExchangeWrapperSettings {
	

	public String getExchangeName();
	public <E extends Enum<E>> IExchangeWrapperSettings setPeriodLength(E periodLength);
	public <E extends Enum<E>> IExchangeWrapperSettings setCurrencyPair(E currencyPair);
	public IExchangeWrapperSettings setNumPeriods(int num);
	public int getPeriodLength();
	public boolean arePrarametersFine();
	boolean isValidPeriodLength(int periodLength);
	public PossibleExchanges getExchange();
	public boolean isUpdating();
	public IExchangeWrapperSettings setIsUpdating(boolean b);
	<E extends Enum<E>> E getCurrencyPair();
	public String getPeriodLengthAsString();
	public String getPairAsString();
}
