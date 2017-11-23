package server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import server.model.Message;

public class ClientThread extends Thread {
	private Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	JTextArea textArea;
	
	public ClientThread(Socket socket, JTextArea textArea) {
		this.socket = socket;
		this.textArea = textArea;
		
		// TODO check if nickname exists
		
		try {
			input = (ObjectInputStream) socket.getInputStream();
			Message message = (Message) input.readObject();
			ServerThread.clients.put(message.getNickName(), this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		
	}

	public void sendMessage(Message message) {
		
	}
	
	public void reSendAll(Message message) {
		
	}
	
	public void disconnect() {
		
	}
}
