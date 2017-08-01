package de.daniel.dengler.getRichBot.exchangewrappers;

import java.io.IOException;

import de.daniel.dengler.getRichBot.ChartDataPoint;


public interface IExchangeConnector {

	ChartDataPoint[] getChart(IExchangeWrapperSettings settings) throws IOException;


}
