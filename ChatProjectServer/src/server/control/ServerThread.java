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
	public static ConcurrentSkipListMap<String, ClientThread> clients = new ConcurrentSkipListMap<>();
	/**
	 * Server's graphical user interface to display messages.
	 */
	private JTextArea textArea;
	
	private DefaultListModel<String> model;

	/**
	 * Constructs the server thread instance, initializes the text are attribute and
	 * starts the servers main thread.
	 * 
	 * @param textArea
	 *            text area of the graphical user interface.
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
				
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) input.readObject();
				
				
				if (!ServerThread.clients.containsKey(message.getNickName())) {
					output.writeObject(new Message(Message.SERVER_NICK, Message.APPROVE));
					// Create the client thread for the newly accepted connection.
					ClientThread client = new ClientThread(socket, textArea, output, input, model);
					List<String> keys = new ArrayList<>(ServerThread.clients.keySet());
					int pos = keys.indexOf(message.getNickName());
					model.add(pos, message.getNickName());
				} else {
					output.writeObject(new Message(Message.SERVER_NICK, Message.REJECT));
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
