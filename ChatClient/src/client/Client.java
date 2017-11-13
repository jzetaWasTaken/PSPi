package client;

import java.net.Socket;

public class Client {

	public final static String IP = "127.0.0.1";
	public final static int PORT = 5002;
	
	public static void main(String[] args) {
		Socket client = null;
		try {
			client = new Socket(IP, PORT);
			ThreadSend ts = new ThreadSend(client);
			ThreadReceive tr = new ThreadReceive(client);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
