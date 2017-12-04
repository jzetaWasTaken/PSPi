package server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import model.Message;

public class ClientThread extends Thread {
	private Socket socket;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private JTextArea textArea;
	
	public ClientThread(Socket socket, JTextArea textArea) {
		this.socket = socket;
		this.textArea = textArea;
		
		// TODO check if nickname exists
		
		try {
			output = new ObjectOutputStream(this.socket.getOutputStream());
			input = new ObjectInputStream(this.socket.getInputStream());
			Message message = (Message) input.readObject();
			textArea.append(message.toString());
			ServerThread.clients.put(message.getNickName(), this);
			reSendAll(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		start();
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				if (input == null) System.out.println("Input null");
				else {
					Message message = (Message) input.readObject();
					reSendAll(message);
					textArea.append(message.toString());
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(Message message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reSendAll(Message message) {
		for (ClientThread client : ServerThread.clients.values()) {
			client.sendMessage(message);
		}
	}
	
	public void disconnect() {
		
	}
}
