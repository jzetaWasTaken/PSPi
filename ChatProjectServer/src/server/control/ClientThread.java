package server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
					if (message.getText().equals(Message.BYE_MSG)) {
						textArea.append(message.toString());
						ServerThread.clients.remove(message.getNickName());
						reSendAll(message);
						break;
					}
					reSendAll(message);
					textArea.append(message.toString());
				}
			}
		} catch (SocketException e) {
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void sendMessage(Message message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reSendAll(Message message) {
		for (ClientThread client : ServerThread.clients.values()) {
			client.sendMessage(message);
		}
	}
	
	public void disconnect() {
		try {
			if (output != null)
				output.close();
			if (input != null)
				input.close();
			if (socket != null)
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
