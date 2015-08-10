package com.mj.gui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mj.gui.StationClient.OnDataPass;


public class MainGUI  {

	private JFrame frame;
	private Container container;


	private static StationClient station;
	private FileWatcher fileWatcher;
	private Thread fileWatcherThread;

	public static void main(String[] args) {

		MainGUI window = new MainGUI();

	}

	//constructor
	public MainGUI() {
		makeGUIThings();
		//openEntrance();

		//starting file watcher in new thread
		fileWatcherThread = new Thread(new Runnable() {

			@Override
			public void run() {
				fileWatcher = new Watcher();
				fileWatcher.init();
			}
		});
		fileWatcherThread.start();

		long time = System.currentTimeMillis();
		station = new StationClient();
		//testConnection();
		System.out.println("Time: "+(System.currentTimeMillis() - time)+"ms");


	}

	private void makeGUIThings() {
		frame = new JFrame("BRT");
		//SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowEventHandler());

		container = new Container();

		frame.add(container);
		frame.setVisible(true);
		SwingUtilities.updateComponentTreeUI(frame);
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
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

	class Watcher extends FileWatcher { 
		public void read(String path) {
			String uid = readFile(path);
			String response = station.push(uid);
			doEntrance(response);
			container.dispInfo(uid + " : "+response);
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

	public String readFile(String path) {
		String result = null;
		try {
			result =  new String(Files.readAllBytes(Paths.get(path)));
			result = result.substring(8,16);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		};
		return result;
	}

	public void doEntrance(String response) {
		boolean shouldOpen = response.startsWith("OPEN");
		if (shouldOpen) {
			System.out.println("Should open the entrance");
			openEntrance();
		}
	}

	private void openEntrance() {
		String open_command = "nircmd cdrom open e:";
		String close_command = "nircmd cdrom close e:";

		Runtime rt = Runtime.getRuntime();

		try {
			rt.exec(open_command);
			//sleep for a little
			try {
				Thread.sleep(3000);               
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			rt.exec(close_command);
			System.out.println("Should open the entrance");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
