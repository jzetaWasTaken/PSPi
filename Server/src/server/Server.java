package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public final static int PORT = 5002;
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket client = null;
		try {
			ss = new ServerSocket(PORT);
			while (1 < 2) {
				client = ss.accept();
				ThreadServer ts = new ThreadServer(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
