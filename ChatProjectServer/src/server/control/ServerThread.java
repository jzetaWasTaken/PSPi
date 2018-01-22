package server.control;

import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.swing.DefaultListModel;
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
	static ConcurrentSkipListMap<String, ClientThread> clients = new ConcurrentSkipListMap<>();
	/**
	 * Server's graphical user interface to display messages.
	 */
	private JTextArea textArea;
	/**
	 * Model for the user list.
	 */
	private DefaultListModel<String> model;

	/**
	 * Constructs the server thread instance, initializes the text are attribute and
	 * starts the servers main thread.
	 * 
	 * @param textArea
	 *            text area of the graphical user interface.
	 * @param model
	 *            user list model
	 */
	public ServerThread(JTextArea textArea, DefaultListModel<String> model) {
		// Initialize text area attribute.
		this.textArea = textArea;
		this.model = model;
		// Start running the server thread.
		start();
	}

	/**
	 * <code>ServerThread</code>'s main instruction block. It creates the
	 * <code>ServerSocket</code> instance and it listens to client connections while
	 * the server is running.
	 */
	@Override
	@SuppressWarnings("unused")
	public void run() {
		// I/O streams.
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			// Create the server socket.
			server = new ServerSocket(PORT);
			
			// Loop to listen to incoming connections.
			while (true) {
				// Accept the connection.
				Socket socket = server.accept();

				// Initialize I/O streams to check incoming connection nickname.
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());

				// Get initial client message to check nickname.
				Message message = (Message) input.readObject();

				// Check nickname.
				if (!ServerThread.clients.containsKey(message.getNickName())) {
					// If the nickname is valid, send approve message.
					output.writeObject(new Message(Message.SERVER_NICK, Message.APPROVE));

					// Create the client thread for the newly accepted connection.
					ClientThread client = new ClientThread(socket, textArea, output, input, model);

					// Get the position in which the new user should be added to the list according
					// to alphabetical order.
					List<String> clientKeys = new ArrayList<>(ServerThread.clients.keySet());
					int pos = clientKeys.indexOf(message.getNickName());

					// Add the user to the list in the given position.
					model.add(pos, message.getNickName());
				} else {
					// Else send rejection message.
					output.writeObject(new Message(Message.SERVER_NICK, Message.REJECT));
					
					// And close the socket.
					socket.close();
				}
			}
		} catch (SocketException e) {
			// When the server socket is closed, end program.
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Close network and I/O resources.
			try {
				if (server != null)
					server.close();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
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
