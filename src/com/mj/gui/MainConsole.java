package com.mj.gui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;


public class MainConsole{

	static StationClient station;
	static FileWatcher fileWatcher;

	public static void main(String[] args) {

		station  = new StationClient();
		station.init();

		//fileWatcher = new FileWatcher();
		//fileWatcher.init();

	}

	public static void readCardUid(Object context) {
		File file = new File("data/"+context.toString());
		try {

			BufferedReader br = new BufferedReader(new FileReader(file));
			String result = br.readLine();
			result = result.substring(8, 16);
			br.close();//release the file..
			

			System.out.println(result);
			station.send(result);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("Error file not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Error IO in reading file");
		}
	}

}









