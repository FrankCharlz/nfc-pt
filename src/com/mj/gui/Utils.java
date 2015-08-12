package com.mj.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Utils {

	private Utils() {}

	public static void openEntrance() {
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


	public static String readFile(String path) {
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

	private static final float SAMPLE_RATE = 8000f;

	public static void beep() {


		try {

			tone(41000,300, 1);Thread.sleep(200);
			tone(9000,100, 1);Thread.sleep(200);
			tone(1000,400, 1);

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private final static void tone(int hz, int msecs, double vol)
			throws LineUnavailableException 
	{
		byte[] buf = new byte[1];
		AudioFormat af = 
				new AudioFormat(
						SAMPLE_RATE, // sampleRate
						8,           // sampleSizeInBits
						1,           // channels
						true,        // signed
						false);      // bigEndian
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (int i=0; i < msecs*8; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
			sdl.write(buf,0,1);
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}

	public static String getWorkingDirectory() {
		String path = "C:\\ProgramData\\brt\\data\\";

		String ins = "COPY THE PATH BELOW AND INTEGRATE IT IN GOTOTAGS APP:"+System.lineSeparator();
		//URL location = Utils.class.getProtectionDomain().getCodeSource().getLocation();

		File file_path = new File(path);
		if (!file_path.exists()) {
			file_path.mkdirs();
		}

		File result_file = new File(path+"result.txt");
		if (result_file.exists()) {
			return result_file.getAbsolutePath();
		}
		boolean success = false;
		try {
			success = result_file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (success) ? result_file.getAbsolutePath() : "Result file could not be created plz restart";
	}

	public static void updateAdress() {
		
		
	}

}
