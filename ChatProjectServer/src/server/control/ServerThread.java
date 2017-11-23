package server.control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTextArea;

public class ServerThread extends Thread {
	public final static int PORT = 5100;
	private ServerSocket server = null;
	static ConcurrentHashMap<String, ClientThread> clients = 
			new ConcurrentHashMap<>();
	private JTextArea textArea;
	
	public ServerThread(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void run() {
		ObjectOutputStream output = null;
		try {
			server = new ServerSocket(PORT);
			while (true) {
				Socket socket = server.accept();
				ClientThread client = new ClientThread(socket, textArea);
				client.start();
				output = (ObjectOutputStream) socket.getOutputStream();
				// output.writeObject(new Message());
				// TODO seguir aquí
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void disconnect() {
		
	}
}
