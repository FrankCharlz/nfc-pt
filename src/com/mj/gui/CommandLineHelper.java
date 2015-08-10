package com.mj.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CommandLineHelper {

	public static String executeCommand(String command) {

		try {
			Process child = Runtime.getRuntime().exec(command);

			// Get output stream to write from it
			OutputStream out = child.getOutputStream();

			
		} catch (IOException e) {
		}
		return "";
	}


}
