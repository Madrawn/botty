package de.daniel.dengler.getRichBot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class Switchclient{

	String apiKey, apiSecret;

	WebSocket mySocket;
	
	public Switchclient(String URI, ApiKeySecretPair key) throws WebSocketException, IOException{
		
		this(URI, key.getApiKey(), key.getApiSecret());
	}


	public Switchclient(String URI, String key, String secret)
			throws WebSocketException, IOException {
		this.mySocket = new WebSocketFactory()
				.createSocket("wss://api.bitfinex.com/ws/2")
				.addListener(new WebSocketAdapter() {
					@Override
					public void onTextMessage(WebSocket ws, String message) {
						System.out.println("Received msg: " + message);
						try {
							JsonParser jp = new JsonFactory().setCodec(
									new ObjectMapper()).createParser(message);
							if (jp != null) {
								TreeNode treeRoot = jp.readValueAsTree();
								System.out.println("Read treeRoot ");
								if (treeRoot.isContainerNode()) {

									if (treeRoot.isArray()) {
										ArrayNode root = (ArrayNode) treeRoot;
										processArray(root);
									}
									if (treeRoot.isObject()) {
										ObjectNode root = (ObjectNode) treeRoot;
										processObject(root);
									}
								} else {
									System.out.println("Unexpected Message");
								}

							} else {
								System.out.println("Couldn't read the message");
							}
						} catch (JsonParseException e) {
							System.out.println("Couldn't read the message");
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onConnected(WebSocket websocket,
							Map<String, List<String>> headers) throws Exception {
						// TODO Auto-generated method stub
						super.onConnected(websocket, headers);
						setUpAuth(websocket, key, secret);
					}
				}).connect();
	}

	public static String encode(String key, String data) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA384");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"),
				"HmacSHA256");
		sha256_HMAC.init(secret_key);
		StringBuilder sb = new StringBuilder();
		byte[] doFinal = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		for (byte b : doFinal) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	protected void setUpAuth(WebSocket websocket, String key, String secret)
			throws Exception {
		JsonFactory fac = new JsonFactory();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonGenerator jg = fac.createGenerator(out);

		long nonce = new Date().getTime() * 1000;
		String authSigV;
		String payload = "AUTH" + nonce;

		authSigV = encode(secret, payload);
		jg.writeStartObject();
		jg.writeStringField("event", "auth");
		jg.writeStringField("apiKey", key);
		jg.writeStringField("authPayload", payload);
		jg.writeStringField("authSig", authSigV);
		jg.writeStringField("authNonce", "" + nonce);
		jg.writeEndObject();
		jg.flush();
		websocket.sendText(out.toString());


	}

	/**
	 * @param treeRoot
	 */
	protected void processArray(ArrayNode treeRoot) {
		String cid, title;
		cid = treeRoot.get(0).toString();
		title = treeRoot.get(1).toString();
		System.out.println("Array with channel: " + cid + " and title: "
				+ title);
	}

	/**
	 * @param root
	 */
	protected void processObject(ObjectNode root) {
		String event = root.findValue("event").textValue();
		if (event != null)
			System.out.println("Event: " + event);
	}


	public ChartDataPoint getLatestCandle(String timeFrame, String symbol) {
		try {
			HttpsURLConnection c = (HttpsURLConnection) new URL("https://api.bitfinex.com/v2/candles/trade:"+timeFrame+":"+symbol+"/last").openConnection();
			c.connect();
			c.getContentType();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
