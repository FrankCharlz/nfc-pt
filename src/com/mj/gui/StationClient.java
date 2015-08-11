package com.mj.gui;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class StationClient {

	private Socket socket;

	private OutputStream outToServer;
	private DataOutputStream out;

	private InputStream inFromServer;
	private DataInputStream in;
	
	OnDataPass dataPasser;

	public void init() {
		try {
			System.out.println("Trying to connect to "+Adress.IP+ " at "+Adress.PORT);

			socket = new Socket(Adress.IP, Adress.PORT);

			outToServer = socket.getOutputStream();
			out = new DataOutputStream(new BufferedOutputStream(outToServer));

			inFromServer = socket.getInputStream();
			in = new DataInputStream(new BufferedInputStream(inFromServer));

			System.out.println("Just got connected to "+socket.getRemoteSocketAddress());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String send(String uid) {
		//should be blocking so as user waits until all operations are finished 
		String response = "FAILED";
		try {
			//sending uid
			out.writeUTF(uid);
			out.flush();

			//notify:
			System.out.println("\nMessage sent......................");
			System.out.println("Waiting for response................\n");

			//recievibg response
			response = in.readUTF();
			System.out.println("Server says:" +response);


		} catch (IOException e) {
			System.out.println("An error occured while send/recieving data from server");
		}
		
		return response;
		

	}
	
	public interface OnDataPass {
	    public void onDataPass(String data);
	}

	public void close() {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String push(String uid){
		this.init();
		String res = this.send(uid);
		this.close();
		return res;
		
	}

	

}
