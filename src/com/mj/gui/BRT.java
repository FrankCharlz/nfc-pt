package com.mj.gui;

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
			//gui.dispInfo(uid + " : "+response);
			gui.getLblInfo().setText(uid + " : "+response);


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
			openEntrance();
		} else {
			System.out.println("Should NOT open the entrance");
			SoundHelper.beep();
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


	private void playSound() {
		try {
			File f  = new File("data/audio/sound.wav");

			Clip clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(f);

			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
