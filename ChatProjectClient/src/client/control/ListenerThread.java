package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

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
				Message message = (Message) input.readObject();
				textArea.append(message.toString());
				if (message.getText().equals(Message.DISCON_MSG)) {
					break;
				}
			}
		} catch (SocketException e) {
			// Socket closed thread ends
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  finally {
			btnSend.setEnabled(false);
			try {
				if (input != null) input.close();	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
