package de.daniel.dengler.getRichBot.UI;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

import de.daniel.dengler.getRichBot.ApiKeySecretPair;
import de.daniel.dengler.getRichBot.TradingBot;
import de.daniel.dengler.getRichBot.Watcher;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapper;
import de.daniel.dengler.getRichBot.exchangewrappers.IExchangeWrapperSettings;
import eu.verdelhan.ta4j.Strategy;

public class AllmightyController {

	static NewMainUI mainUI;
	static AllmightyController myself;
	static List<Watcher> watcherList = new ArrayList<Watcher>();
	private static ApiKeySecretPair apiKey;
	
	
	public AllmightyController() {

		if(myself == null)
			myself = this;
	}
	
	public static void loadAPIKey(File key) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(key));
			apiKey = (ApiKeySecretPair) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void setMainUI(NewMainUI m){
		mainUI = m;
	}
	
	static AllmightyController getMyself(){
		if(myself == null)
			myself = new AllmightyController();
		return myself;
	}

	public static void addWatchClicked(ActionEvent arg0,
			LeftList me) {
		new AddWatchDialogOne().setVisible(true);
	}

	public static void mouseGraphClicked(LeftListItem me) {
		Watcher w = me.myController.myWatcher;
		BigWatcherPanel bw = new BigWatcherPanel(w);
		mainUI.centerPanel.addBigPanel(bw);
		mainUI.centerPanel.validate();
	}

	public static void addWatchDialogsDone(
			IExchangeWrapper exchangeWrapper) {
		
		LeftListItem newItem = new LeftListItem();
		mainUI.addNewLeftListItem(newItem);
		LeftListItemController controller = new LeftListItemController(newItem);
		Watcher newWatcher = new Watcher(controller, exchangeWrapper);
		controller.setWatcher(newWatcher);
		new Thread(newWatcher).start();
		watcherList.add(newWatcher);
		
		
		
	}

	public static void showBalanceClicked(JPanel contentPanel, Watcher watcher) {
		// TODO Auto-generated method stub
		
		// I need all balances? No I need only balance of active watcher currency pair
		IExchangeWrapperSettings settings = watcher.getSettings();
		JLabel titleBalance = new JLabel(String.format("Balance for %s and %s", settings.getExchangeName(), settings.getPairAsString()));
		
		
		String frontPair = settings.getPairAsString().substring(0, 3);
		String backPair = settings.getPairAsString().substring(3, 6);
		double firstEBalance = watcher.getBalance("e"+frontPair);
		double secondEBalance = watcher.getBalance("e"+backPair);
		JLabel exchangeBalance = new JLabel(String.format("Exchange %s: %f | %s: %f", frontPair, firstEBalance,backPair,secondEBalance) );
		double firstTBalance = watcher.getBalance("t"+frontPair);
		double secondTBalance = watcher.getBalance("t"+backPair);
		JLabel tradingBalance = new JLabel(String.format("Trading %s: %f | %s: %f", frontPair, firstTBalance,backPair,secondTBalance));
		double firstFBalance = watcher.getBalance("f"+frontPair);
		double secondFBalance = watcher.getBalance("f"+backPair);
		JLabel fundingBalance = new JLabel(String.format("Funding %s: %f | %s: %f", frontPair, firstFBalance,backPair,secondFBalance));
		
		
		
		contentPanel.removeAll();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(titleBalance);
		contentPanel.add(tradingBalance);
		contentPanel.add(exchangeBalance);
		contentPanel.add(fundingBalance);
		contentPanel.validate();
		
	}

	public static ApiKeySecretPair getKey() {
		return apiKey;
	}

	public static void setApiKeyPair(String apiKey2, String apiSecret) {
		apiKey = new ApiKeySecretPair(apiKey2, apiSecret);
	}

	public static void saveAPIKey() {
		if(apiKey!=null){
		    JFileChooser chooser = new JFileChooser();
		    //FileNameExtensionFilter filter = new FileNameExtensionFilter(
		    //    "JPG & GIF Images", "jpg", "gif");
		    //chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(mainUI.getFrame());
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getSelectedFile().getName());
		    }
		    File file = chooser.getSelectedFile();
		   
		    if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("api")) {
		        // filename is OK as-is
		    } else {
		        file = new File(file.toString() + ".api");  // append .api
		    }
		    try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(apiKey);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}else{
			JOptionPane.showMessageDialog(null, "No API Key Pair Set");
		}
		
	}

	public static void addBotClicked(Watcher watcher) {
		
		
	}


	
	
	

}
