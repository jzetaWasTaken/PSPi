package server.control;

import javax.swing.JTextArea;

public class Manager {
	private ServerThread server;
	
	public void startCommunication(JTextArea textArea) {
		server = new ServerThread(textArea);
	}
	
	public void disconnect() {
		server.disconnect();
	}
}
