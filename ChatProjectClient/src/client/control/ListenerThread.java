package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import model.Message;

/**
 * A class representing the client thread that listens to new messages from the
 * server.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see java.lang.Thread
 *
 */
public class ListenerThread extends Thread {
	/**
	 * <code>ObjectInputStream</code> instance to read messages coming from
	 * the server.
	 */
	ObjectInputStream input = null;
	/**
	 * Text area of the graphical user interface to append messages coming
	 * from the server.
	 */
	JTextArea textArea;
	/**
	 * Button to send messages.
	 */
	JButton btnSend;
	/**
	 * <code>Socket</code> class instance to communicate with the server.
	 */
	Socket socket = null;

	/**
	 * Constructs a new <code>ListenerThread</code> class instance and initializes 
	 * all its fields.
	 * 
	 * @param socket	socket to communicate with the server.
	 * @param nickName	user's nickname to register the thread's name.
	 * @param textArea	text area from the graphical user interface.
	 * @param btnSend	button to send messages.
	 * @throws IOException
	 * 			if there is an input/output issue when initializing
	 * 			the <code>ObjectInputStream</code>.
	 */
	ListenerThread(Socket socket, String nickName, JTextArea textArea, JButton btnSend) throws IOException {
		this.setName(nickName);
		this.socket = socket;
		this.textArea = textArea;
		this.btnSend = btnSend;
		this.input = new ObjectInputStream(socket.getInputStream());
		start();
	}

	/**
	 * The main thread execution instructions. The program will read incoming
	 * messages and append them to the text area until it receives a 
	 * disconnection message from the server or an exception is raised.
	 * 
	 * @exception SocketException
	 * 				if there is an error accessing the socket.
	 * @exception IOException
	 * 				if an input/output operation is interrupted.
	 * @exception ClassNotFoundException
	 * 				if an issue raises when trying to load a class.
	 */
	@Override
	public void run() {
		try {
			// Read incoming messages while client is connected.
			while (true) {
				// Read message.
				Message message = (Message) input.readObject();
				
				// Append new message to graphical user interface text area.
				textArea.append(message.toString());
				
				// If server disconnects, break the reading loop to exit.
				if (message.getText().equals(Message.DISCON_MSG)) {
					break;
				}
			}
		} catch (SocketException e) {
			// When the communication socket is closed, end program
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// If the server is disconnected or an error has occurred, disable
			// button to send messages and close I/O resource
			btnSend.setEnabled(false);
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
