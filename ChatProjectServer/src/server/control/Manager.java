package server.control;

import java.io.IOException;

import javax.swing.JTextArea;

public class Manager {
	private ServerThread server;
	
	public void startCommunication(JTextArea textArea) {
		server = new ServerThread(textArea);
	}
	
	public void disconnect() throws IOException {
		server.disconnect();
	}
}
