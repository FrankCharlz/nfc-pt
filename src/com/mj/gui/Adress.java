package com.mj.gui;

public class Adress {

	public static int PORT = 12345;
	public static String IP = "41.86.177.106";
	public static String getAdress() {
		return IP+":"+PORT;
	}
	
	public static void setAdress(String adress) {
		if (!adress.contains(":") || adress.length() < 9)
			return;
		
		//adress in this form x.x.x.x:xxxx
		IP = adress.split(":")[0];
		PORT = Integer.parseInt(adress.split(":")[1]);
	}

}
