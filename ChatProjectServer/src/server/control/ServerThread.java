package server.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTextArea;

import model.Message;

public class ServerThread extends Thread {
	
	public final static int PORT = 5100;
	private ServerSocket server = null;
	static ConcurrentHashMap<String, ClientThread> clients = 
			new ConcurrentHashMap<>();
	private JTextArea textArea;
	
	public ServerThread(JTextArea textArea) {
		this.textArea = textArea;
		start();
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(PORT);
			while (true) {
				Socket socket = server.accept();
				ClientThread client = new ClientThread(socket, textArea);
			}
		} catch (SocketException e) {
			// 
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
		Message message = new Message(Message.SERVER_NICK, Message.DISCON_MSG);
		for (ClientThread client : ServerThread.clients.values()) {
			client.sendMessage(message);
			client.disconnect();
		}
		try {
			if (server != null)
				server.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
