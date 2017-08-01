package de.daniel.dengler.getRichBot.exchangewrappers.bitfinex;

import io.netty.handler.codec.base64.Base64Encoder;
import io.netty.handler.codec.json.JsonObjectDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.ws.client.WebSocketException;

import de.daniel.dengler.getRichBot.ApiKeySecretPair;
import de.daniel.dengler.getRichBot.ChartDataPoint;
import de.daniel.dengler.getRichBot.Logger;
import de.daniel.dengler.getRichBot.Switchclient;
import de.daniel.dengler.getRichBot.UI.AllmightyController;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeConnector;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;

import java.util.Base64;

public class BitfinexWrapperConnector implements IExchangeConnector {

	Switchclient connection;
	private URI websocketURIv2;
	private URI restURIv2;
	private URI restURIv1;

	public BitfinexWrapperConnector() {
		try {
			websocketURIv2 = new URI("wss://api.bitfinex.com/ws/2");
			restURIv2 = new URI("https://api.bitfinex.com/v2/");
			restURIv1 = new URI("https://api.bitfinex.com/v1/");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws WebSocketException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	protected void setup() throws WebSocketException, IOException,
			URISyntaxException {
	}

	public ChartDataPoint[] getChart(IExchangeWrapperSettings settings)
			throws IOException {

		String timeFrame = settings.getPeriodLengthAsString();
		String symbol = "t" + settings.getPairAsString();

		return null;
	}

	public JsonObject newOrder(String symbol, double amount, double price,
			String side, String type, boolean ocoorder, double buy_price_oco,
			double sell_price_oco, ApiKeySecretPair key) {
		String path = "order/new";
		String query = restURIv1.toString() + path;

		long nonce = new Date().getTime();
		JsonObject jo = new JsonObject();
		jo.addProperty("request", path);
		jo.addProperty("nonce", Long.toString(nonce));
		jo.addProperty("symbol", symbol);
		jo.addProperty("amount", amount);
		jo.addProperty("price", price);
		jo.addProperty("side", side);
		jo.addProperty("type", type);
		jo.addProperty("ocoorder", ocoorder);
		jo.addProperty("buy_price_oco", buy_price_oco);
		jo.addProperty("sell_price_oco", sell_price_oco);
		
		String payload = jo.toString();
		
		try {
			HttpsURLConnection c = (HttpsURLConnection) new URL(query)
					.openConnection();

			String sResult = sendAuthWithPayload(payload, c, key);
			Logger.debugLog(sResult);
			JsonObject jResult = new JsonParser().parse(sResult).getAsJsonObject();
			return jResult;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void placeMarketBuyOrder(long amount, ApiKeySecretPair key) {
		// TODO Auto-generated method stub

	}

	public void placeMarketSellOrder(long amount, ApiKeySecretPair key) {
		// TODO Auto-generated method stub

	}

	public double getBalance(String currency, ApiKeySecretPair key) {
		String type = currency.substring(0, 1);
		currency = currency.substring(1, 4);
		if (AllmightyController.getKey() != null) {

			try {
				long nonce = new Date().getTime();
				String urlPath = "/v1/balances";
				JsonObject jo = new JsonObject();
				jo.addProperty("request", urlPath);
				jo.addProperty("nonce", Long.toString(nonce));

				// API v1
				String payload = jo.toString();
				HttpsURLConnection c = (HttpsURLConnection) new URL(
						"https://api.bitfinex.com/v1/balances")
						.openConnection();
				String response = sendAuthWithPayload(payload, c, key);

				Gson g = new Gson();
				BitfinexBalance[] balances = g.fromJson(response,
						BitfinexBalance[].class);

				for (BitfinexBalance b : balances) {
					if (b.currency.equals(currency.toLowerCase())
							&& b.type.startsWith(type))
						return b.amount;

				}

				Logger.normalLog("Did not find currency " + currency
						+ " Type: " + type);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "WARNING.",
					"No API Keypair found! Please set.",
					JOptionPane.WARNING_MESSAGE);
		}

		return -1;
	}

	/**
	 * @param payload
	 * @param c
	 * @param key
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 */
	protected String sendAuthWithPayload(String payload, HttpsURLConnection c,
			ApiKeySecretPair key) throws IOException, MalformedURLException,
			ProtocolException {

		c.setRequestMethod("POST");

		String payload_base64 = Base64.getEncoder().encodeToString(
				payload.getBytes());
		Logger.normalLog("Base64 is:");
		Logger.normalLog(payload_base64);
		String signature = AllmightyController.getKey().getEncoded(
				payload_base64);
		String payload_sha384hmac = key.hmacDigest(payload_base64,
				AllmightyController.getKey().apiSecret, "HmacSHA384");
		String apiKey = AllmightyController.getKey().apiKey;
		System.out
				.println(String
						.format("Sending:\nApiKey: <%s>\nPayload: <%s>\nSignature: <%s> encoded with <%s>",
								apiKey, payload, signature,
								AllmightyController.getKey().apiSecret));
		c.setRequestProperty("X-BFX-APIKEY", apiKey);
		c.setRequestProperty("X-BFX-PAYLOAD", payload_base64);
		c.setRequestProperty("X-BFX-SIGNATURE", payload_sha384hmac);

		c.setRequestProperty("Content-Type", "application/json");
		c.setRequestProperty("Accept", "application/json");

		c.connect();
		String response = c.getResponseMessage();
		Logger.normalLog(response);

		response = new BufferedReader(new InputStreamReader(c.getInputStream()))
				.readLine();
		Logger.normalLog(response);
		return response;
	}

	public ChartDataPoint getMostRecentCandle() {
		try {
			// Available values: '1m', '5m', '15m', '30m', '1h', '3h', '6h',
			// '12h', '1D', '7D', '14D', '1M'
			String timeFrame = getSettings().getPeriodLengthAsString();
			String symbol = getSettings().getPairAsString();
			HttpsURLConnection c = (HttpsURLConnection) new URL(
					"https://api.bitfinex.com/v2/candles/trade:" + timeFrame
							+ ":" + symbol + "/last").openConnection();
			c.connect();
			c.getContentType();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out
					.println("Encountered In/Out problem. Is internetconnection fine?");
			e.printStackTrace();
		}
		return null;
	}

	public void setSettings(IExchangeWrapperSettings settings) {
		// TODO Auto-generated method stub

	}

	public IExchangeWrapperSettings getSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String candles(String timeFrame, String symbol, String scope) {

		String sResult = null;
		String query = restURIv2.toString() + "candles/trade:" + timeFrame
				+ ":t" + symbol + "/" + scope;
		InputStream c;
		try {
			c = new URL(query).openConnection().getInputStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(c));

			sResult = b.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out
					.println("Encountered In/Out problem. Is internetconnection fine?");
			e.printStackTrace();
		}
		if (sResult.equals("null"))
			sResult = null;

		return sResult;
	}

	public String candles(String timeFrame, String symbol, String scope, int num) {
		String sResult = null;
		String query = restURIv2.toString() + "candles/trade:" + timeFrame
				+ ":t" + symbol + "/" + scope + "?limit=" + num;
		InputStream c;
		try {
			c = new URL(query).openConnection().getInputStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(c));
			Logger.normalLog(query);
			sResult = b.readLine();
			Logger.normalLog(sResult);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (sResult.equals("null"))
			sResult = null;

		return sResult;

	}

	public BitfinexOrderBook getOrderBook(String pairAsString) {
		if (pairAsString.length() > 6) {
			Logger.normalLog(pairAsString + " needs to be trimmed");
			pairAsString = pairAsString.substring(1, pairAsString.length());
		}
		BitfinexOrderBook result = null;

		String query = restURIv1.toString() + "book/" + pairAsString;
		InputStream c;
		try {
			c = new URL(query).openConnection().getInputStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(c));
			Logger.verboseLog(query);
			String sResult = b.readLine();
			Logger.verboseLog(sResult);
			result = new Gson().fromJson(sResult, BitfinexOrderBook.class);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

}
