package com.mj.gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class BRT  {

	private static StationClient station;
	private FileWatcher fileWatcher;
	private Thread fileWatcherThread;
	private Container gui;

	public static void main(String[] args) {

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
		gui = new Container();

		gui.addWindowListener(new WindowEventHandler());
	}

	

	class Watcher extends FileWatcher { 
		public void read(String path) {
			String uid = Utils.readFile(path);
			String response = station.push(uid);
			processResponse(uid, response);
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
		gui.getLblInfo().setText(uid + " : "+response);
		if (shouldOpen) {
			gui.getLblInfo().setBackground(Color.GREEN);
			Utils.openEntrance();
			gui.getLblUid().setBackground(Color.WHITE);
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
