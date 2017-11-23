package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ListenerThread extends Thread {
	ObjectInputStream input = null;
	JTextArea textArea;
	JButton btnSend;
	Socket socket = null;

	ListenerThread(Socket socket, JTextArea textArea, JButton btnSend) {
		this.socket = socket;
		this.textArea = textArea;
		this.btnSend = btnSend;
	}

	@Override
	public void run() {
		try {
			input = (ObjectInputStream) socket.getInputStream();
			while (true) {
				textArea.setText(input.readObject().toString());
			}
		} catch (IOException e) {
			// TODO handle error. Inform user
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
