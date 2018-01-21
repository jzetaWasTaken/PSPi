package server.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTextArea;
import model.Message;

/**
 * Class representing the main server thread to listen to client connections.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see java.lang.Thread
 */
public class ServerThread extends Thread {

	/**
	 * The port in which the server will be listening for client connections.
	 */
	public final static int PORT = 5100;
	/**
	 * The <code>ServerSocket</code> instance.
	 */
	private ServerSocket server = null;
	/**
	 * Map containing the client threads of all the connected users.
	 */
	static ConcurrentHashMap<String, ClientThread> clients = new ConcurrentHashMap<>();
	/**
	 * Server's graphical user interface to display messages.
	 */
	private JTextArea textArea;

	/**
	 * Constructs the server thread instance, initializes the text are attribute and
	 * starts the servers main thread.
	 * 
	 * @param textArea
	 *            text area of the graphical user interface.
	 */
	public ServerThread(JTextArea textArea) {
		// Initialize text area attribute.
		this.textArea = textArea;
		// Start running the server thread.
		start();
	}

	/**
	 * <code>ServerThread</code>'s main instruction block. It creates the
	 * <code>ServerSocket</code> instance and it listens to client connections while
	 * the server is running.
	 * 
	 * @exception SocketException
	 *                if there is an error accessing the socket.
	 * @exception IOException
	 *                if an input/output operation is interrupted.
	 * @exception ClassNotFoundException
	 *                if an issue raises when trying to load a class.
	 */
	@Override
	@SuppressWarnings("unused")
	public void run() {
		try {
			// Create the server socket.
			server = new ServerSocket(PORT);

			// Loop to listen to incoming connections.
			while (true) {
				// Accept the connection.
				Socket socket = server.accept();

				// Create the client thread for the newly accepted connection.
				ClientThread client = new ClientThread(socket, textArea);
			}
		} catch (SocketException e) {
			// When the server socket is closed, end program.
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Close network resources
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Method to disconnect the server. It sends a disconnection message to the
	 * clients, it disconnects the clients and it closes the server socket.
	 * 
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 */
	public void disconnect() throws IOException {
		// Create disconnection message.
		Message message = new Message(Message.SERVER_NICK, Message.DISCON_MSG);

		// Loop through all the clients.
		for (ClientThread client : ServerThread.clients.values()) {
			// Send the disconnection message.
			client.sendMessage(message);

			// Disconnect the client.
			client.disconnect();
		}
		// Close the server socket.
		if (server != null)
			server.close();
	}
}
