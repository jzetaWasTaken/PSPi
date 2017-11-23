package client.control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import client.model.Message;

public class Manager {
	public static final int PORT = 5100;
	public static final String HOST = "127.0.0.1";
	private Socket socket = null;
	ObjectOutputStream output = null;
	ListenerThread listener;
	
	public void startCommunication(String nickName, JTextArea textArea, JButton btnSend) {
		new ListenerThread(socket, textArea, btnSend).start();
	}
	
	public void sendMessage(Message message) {
		try {
			output = (ObjectOutputStream) socket.getOutputStream();
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String nickName) throws UnknownHostException, IOException  {
		socket = new Socket(HOST, PORT);
		sendMessage(new Message(nickName, Message.HELLO_MSG));
	}
	
	public void disconnect(String nickName) {
		
	}
}
