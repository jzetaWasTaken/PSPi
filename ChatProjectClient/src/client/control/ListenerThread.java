package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JTextArea;

import model.Message;

public class ListenerThread extends Thread {
	ObjectInputStream input = null;
	JTextArea textArea;
	JButton btnSend;
	Socket socket = null;

	ListenerThread(Socket socket, JTextArea textArea, JButton btnSend) {
		this.socket = socket;
		this.textArea = textArea;
		this.btnSend = btnSend;
		try {
			this.input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				textArea.setText(((Message)input.readObject()).toString());
			}
		} catch (IOException e) {
			// TODO handle error. Inform user
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (input != null) input.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
