package client.control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import model.Message;

/**
 * Class representing a manager to handle the main operations between server and
 * client such as: connection, disconnection and message sending.
 * 
 * @author Jon Zaballa
 * @version 1.0
 *
 */
public class Manager {
	/**
	 * Server's port.
	 */
	public static final int PORT = 5100;
	/**
	 * Server's IP address.
	 */
	public static final String HOST = "127.0.0.1";
	/**
	 * Server/Client communication socket.
	 */
	private Socket socket = null;
	/**
	 * <code>ObjectOutputStream</code> class instance to write messages and send
	 * them to the server.
	 */
	ObjectOutputStream output = null;

	/**
	 * Method to create the <code>ListenerThread</code> instance for the client.
	 * 
	 * @param nickName
	 *            user nickname.
	 * @param textArea
	 *            graphical user interface text area for messages.
	 * @param btnSend
	 *            button to send messages.
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 */
	public void startCommunication(String nickName, JTextArea textArea, JButton btnSend) throws IOException {
		new ListenerThread(socket, nickName, textArea, btnSend);
	}

	/**
	 * Method to send message via the <code>ObjectOutputStream</code> instance of
	 * the communication socket.
	 * 
	 * @param message
	 *            message to be sent
	 * @throws SocketException
	 *             if there is an error accessing the socket.
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 */
	public void sendMessage(Message message) throws SocketException, IOException {
		output.writeObject(message);
	}

	/**
	 * 
	 * Method to create the communication socket instance, initialize its
	 * <code>ObjectOutputStream</code> object and send the <i>Hello message</i>.
	 * 
	 * @param nickName
	 *            user's nickname to send the first message.
	 * @throws UnknownHostException
	 *             if the IP address of the host can not be determined.
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 */
	public void connect(String nickName) throws UnknownHostException, IOException {
		// Create communication socket.
		socket = new Socket(HOST, PORT);
		
		// Initialize the output stream
		output = new ObjectOutputStream(socket.getOutputStream());
		
		// Send initial "Hello message"
		sendMessage(new Message(nickName, Message.HELLO_MSG));
	}

	/**
	 * Method to disconnect from the server. It sends <i>Bye message</i> and it
	 * closes the network and I/O resources
	 * 
	 * @param nickName
	 * @throws SocketException
	 * @throws IOException
	 */
	public void disconnect(String nickName) throws SocketException, IOException {
		// Send last "Bye message"
		sendMessage(new Message(nickName, Message.BYE_MSG));
		
		// Close network and I/O resources
		if (output != null)
			output.close();
		if (socket != null)
			socket.close();
	}
}
