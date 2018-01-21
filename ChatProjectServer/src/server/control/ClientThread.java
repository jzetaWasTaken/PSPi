package server.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JTextArea;
import model.Message;

/**
 * Class representing the thread to listen to the incoming messages from a
 * client and in charge of sending those messages to the rest of the users.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see java.lang.Thread
 */
public class ClientThread extends Thread {

	/**
	 * The communication socket between client and server.
	 */
	private Socket socket;
	/**
	 * <code>ObjectInputStream</code> instance to read incoming messages from.
	 */
	private ObjectInputStream input = null;
	/**
	 * <code>ObjectOutputStream</code> instance to write the messages to be sent to
	 * other users.
	 */
	private ObjectOutputStream output = null;
	/**
	 * Text area of the server graphical user interface to display chat messages.
	 */
	private JTextArea textArea;

	/**
	 * Constructs the <code>ClientThread</code> class instance. It initializes the
	 * socket, the text area and the I/O resources. Moreover, it reads the <i>Hello
	 * message</i> from the client and it sends it to the rest of the users calling
	 * the appropriate method: <code>resendAll(Message message)</code>. It also
	 * keeps an instance of itself in <code>ServerThread</code>'s
	 * <code>clients</code> map.
	 * 
	 * @param socket
	 * @param textArea
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 * @throws ClassNotFoundException
	 *             if an issue raises when trying to load a class.
	 */
	public ClientThread(Socket socket, JTextArea textArea) throws IOException, ClassNotFoundException {
		// Initializes fields.
		this.socket = socket;
		this.textArea = textArea;
		output = new ObjectOutputStream(this.socket.getOutputStream());
		input = new ObjectInputStream(this.socket.getInputStream());

		// Gets the initial message and append it to the text area.
		Message message = (Message) input.readObject();
		textArea.append(message.toString());

		// Puts a reference to itself in ServerThread's map
		ServerThread.clients.put(message.getNickName(), this);

		// Send the previously retrieved "Hello message" to all users
		reSendAll(message);

		// Start the thread
		start();
	}

	/**
	 * Main execution instructions for the client thread. It reads messages from the
	 * input stream continuously. It displays read messages in the server's text
	 * area and send them to the rest of the users. If it reads a "Bye message", it
	 * removes the client thread from the server's map, it resends the message to 
	 * the rest of the clients and it exits the reading loop.
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
			while (true) {
				// Check if the input stream is null.
				if (input == null)
					System.out.println("Input null");
				else {
					// Read the message.
					Message message = (Message) input.readObject();
					// Check if it is a "Bye message"
					if (message.getText().equals(Message.BYE_MSG)) {
						// Append the "Bye message" to text area.
						textArea.append(message.toString());
						// Remove client thread from server map.
						ServerThread.clients.remove(message.getNickName());
						// Send "Bye message" to all users.
						reSendAll(message);
						// Exit the reading loop.
						break;
					}
					// Send message to all users.
					reSendAll(message);
					// Display message in the server's user interface.
					textArea.append(message.toString());
				}
			}
		} catch (SocketException e) {
			// When the communication socket is closed, end program.
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Call disconnection method.
			disconnect();
		}
	}
	
	/**
	 * Method that sends the message to another client.
	 * 
	 * @param message	message received and to be sent.
	 * @exception IOException
	 * 				if an input/output operation is interrupted.
	 */
	public void sendMessage(Message message) {
		try {
			// Write the message to the stream.
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the send message for every client currently connected to the server.
	 * 
	 * @param message	message received and to be sent.
	 */
	public void reSendAll(Message message) {
		for (ClientThread client : ServerThread.clients.values()) {
			client.sendMessage(message);
		}
	}
	
	/**
	 * Method to close all the I/O and network resources.
	 * 
	 * @exception IOException
	 * 				if an input/output operation is interrupted.
	 */
	public void disconnect() {
		try {
			if (output != null)
				output.close();
			if (input != null)
				input.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
