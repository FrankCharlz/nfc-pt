package com.mj.gui;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public abstract class FileWatcher {

	private static final String DIRECTORY = "data/";
	private static WatchService watcher;

	
	public abstract void read(String path);
	
	public void init() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(DIRECTORY);
			dir.register(watcher, ENTRY_MODIFY);


			System.out.println("Watch Service registered for dir: " + dir.getFileName());

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;//exits the whole method..
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					
					read(DIRECTORY+ev.context());

					System.out.println(kind.name() + ": " +ev.context()); 
					

				}

				//reset the key so as you can take the next event..
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public void close() {
		try {
			if (watcher != null)
				watcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}