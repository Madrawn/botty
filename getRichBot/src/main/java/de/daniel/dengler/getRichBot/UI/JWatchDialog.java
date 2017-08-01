package de.daniel.dengler.getRichBot.UI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public class JWatchDialog extends JDialog {
	
	public JWatchDialog(){
		AllmightyController.mainUI.getFrame().setEnabled(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				AllmightyController.mainUI.getFrame().setEnabled(true);
				AllmightyController.mainUI.getFrame().requestFocus();
				dispose();
			}
		});
	}

}
