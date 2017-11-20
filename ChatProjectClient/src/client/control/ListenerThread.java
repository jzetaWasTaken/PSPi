package client.control;

import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class ListenerThread extends Thread {
	ObjectInputStream input;
	JTextArea textArea;
	JButton btnSend;

	ListenerThread(ObjectInputStream input, JTextArea textArea, JButton btnSend) {
		this.input = input;
		this.textArea = textArea;
		this.btnSend = btnSend;
	}

	@Override
	public void run() {
		
	}
}
