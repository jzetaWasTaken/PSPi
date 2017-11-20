package client.control;

import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JTextArea;

import model.Message;

public class Manager {
	public static final int PORT = 5100;
	public static final String HOST = "127.0.0.1";
	private Socket socket;
	ObjectOutputStream output;
	ListenerThread listener;
	
	
	
	public void startCommunication(String nickName, JTextArea textArea, JButton btnSend) {
		
	}
	
	public void sendMessage(Message message) {
		
	}
	
	public void connect(String nickName) {
		
	}
	
	public void disconnect(String nickName) {
		
	}
}
