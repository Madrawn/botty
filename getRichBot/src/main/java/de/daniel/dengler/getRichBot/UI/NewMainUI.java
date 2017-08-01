package de.daniel.dengler.getRichBot.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NewMainUI {

	private JFrame frame;
	public LeftList leftList;
	public CenterPanel centerPanel;
	private JPanel buttonPanel;

	protected JFrame getFrame() {
		return frame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMainUI window = new NewMainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewMainUI() {
		AllmightyController.setMainUI(this);
		File defaultKey = new File("default.api");
		if(defaultKey.exists()){
			AllmightyController.loadAPIKey(defaultKey);
			System.out.println("Default Keypair loaded.");
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane()
				.getLayout();
		borderLayout.setVgap(15);
		borderLayout.setHgap(15);
		frame.setBounds(100, 100, 1031, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenu mnApiAuth = new JMenu("API Auth");
		mnSettings.add(mnApiAuth);
		
		JMenuItem mntmLoadApiCredentials = new JMenuItem("Load Api Credentials");
		mntmLoadApiCredentials.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "API Key File", "api");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showSaveDialog(getFrame());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			    }
			    File file = chooser.getSelectedFile();
			    AllmightyController.loadAPIKey(file);
				
			}
		});
		mnApiAuth.add(mntmLoadApiCredentials);
		
		JMenuItem mntmSaveApiCredentials = new JMenuItem("Save Api Credentials");
		mntmSaveApiCredentials.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AllmightyController.saveAPIKey();
			}
		});
		mnApiAuth.add(mntmSaveApiCredentials);
		
		JSeparator separator = new JSeparator();
		mnApiAuth.add(separator);
		
		JMenuItem mntmSetApiCredentials = new JMenuItem("Set Api Credentials");
		mntmSetApiCredentials.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Trying to set API KeySecret");
				
				String apiKey = JOptionPane.showInputDialog(frame, "Please enter your API Key.");
				String apiSecret = JOptionPane.showInputDialog(frame, "Please enter your API Secret.");
				
				JOptionPane.showMessageDialog(frame, String.format("You have chosen as API Key: <%s> and as API Secret: <%s>", apiKey, apiSecret));
				
				AllmightyController.setApiKeyPair(apiKey, apiSecret);
			}
		});
		mnApiAuth.add(mntmSetApiCredentials);

		leftList = new LeftList();
		frame.getContentPane().add(leftList, BorderLayout.WEST);

		centerPanel = new CenterPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public void addNewLeftListItem(LeftListItem newItem) {
		leftList.addNewItem(newItem);
	}

}
