package com.mj.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class BRT  {

	private static StationClient station;
	private FileWatcher fileWatcher;
	private Thread fileWatcherThread;
	private View view;

	public static void main(String[] args) {
		Utils.updateAdress();
		BRT window = new BRT();

	}

	//constructor
	public BRT() {

		//starting file watcher in new thread
		fileWatcherThread = new Thread(new Runnable() {

			@Override
			public void run() {
				fileWatcher = new Watcher();
				fileWatcher.init();
			}
		});

		fileWatcherThread.start();
		station = new StationClient();
		
		view = new View();
		view.getTxtIP().setText(Adress.IP);
		view.getTxtPort().setText(Adress.PORT+"");
		view.getTxtPath().setText(Utils.getWorkingDirectory());
		view.getBtnSaveAddress().addActionListener(new ButtonClicks());
		
		JFrame frame = new JFrame("BRT");
		//frame.setLayout(new FlowLayout());
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowEventHandler());
		frame.add(view);
		frame.addWindowListener(new WindowEventHandler());
		frame.setVisible(true);
		
		SwingUtilities.updateComponentTreeUI(frame);
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	class Watcher extends FileWatcher { 
		public void read(String path) {
			String uid = Utils.readFile(path);
			String response = station.push(uid);
			processResponse(uid, response);
		}

	}
	
	class ButtonClicks implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Adress.IP = view.getTxtIP().getText();
				Adress.PORT = Integer.parseInt(view.getTxtPort().getText());
				view.getTxtDisplay().setText("Server at: "+Adress.getAdress());

			} catch (NumberFormatException err) {
				err.printStackTrace();
			}
		}
		
	}

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			station.close();
			fileWatcher.close();
			try {
				fileWatcherThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Exiting"); 
		}
	}

	public void processResponse(String uid, String response) {
		boolean shouldOpen = response.startsWith("OPEN");
		view.getTxtDisplay().setText(uid + " : "+response);
		if (shouldOpen) {
			Utils.openEntrance();
		} else {
			System.out.println("Should NOT open the entrance");
			Utils.beep();
		}
	}
	
	private void testConnection() {
		long time = System.currentTimeMillis();
		int i;
		for (i=0; i<1; i++) {
			station.push("abeoe70b");
		}
		System.out.println("Sent "+i+" uids in "+(System.currentTimeMillis() - time)+"ms");

	}
	
	

	


}
