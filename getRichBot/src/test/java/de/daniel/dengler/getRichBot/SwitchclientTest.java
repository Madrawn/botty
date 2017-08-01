package de.daniel.dengler.getRichBot;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.neovisionaries.ws.client.WebSocketException;

public class SwitchclientTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		try {
			Switchclient test = new Switchclient("wss://api.bitfinex.com/ws/2",
					"K91uFM209zYsAPJKT5ykhOTd2LKbOSaHOC2pYaVh7nG",
					"un2D7K2wubnk1cEsDQsCWYB1YWkzodJ3RLsj4QPnowK");
			Thread.sleep(5000);
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fail("Not yet implemented");
	}

}
