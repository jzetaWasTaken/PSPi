package client.control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JTextArea;

import model.Message;

public class Manager {
	public static final int PORT = 5100;
	public static final String HOST = "127.0.0.1";
	private Socket socket = null;
	ObjectOutputStream output = null;
	ListenerThread listener;
	
	public void startCommunication(String nickName, JTextArea textArea, JButton btnSend) {
		new ListenerThread(socket,nickName, textArea, btnSend);
	}
	
	public void sendMessage(Message message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String nickName) throws UnknownHostException, IOException  {
		socket = new Socket(HOST, PORT);
		output = new ObjectOutputStream(socket.getOutputStream());
		sendMessage(new Message(nickName, Message.HELLO_MSG));
	}
	
	public void disconnect(String nickName) {
		sendMessage(new Message(nickName, Message.BYE_MSG));
			try {
				if (output != null)
					output.close();
				if (socket != null) 
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
