package server.control;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import model.Message;

/**
 * Class representing a manager to handle the main operations between server and
 * client.
 * 
 * @author Jon Zaballa
 * @version 1.0
 *
 */
public class Manager {

	/**
	 * Instance of the server thread.
	 */
	private ServerThread server;

	/**
	 * Method to create the server's main thread.
	 * 
	 * @param textArea
	 *            text area of the server's graphical user interface.
	 */
	public void startCommunication(JTextArea textArea, DefaultListModel<String> model) {
		server = new ServerThread(textArea, model);
	}

	/**
	 * Method to disconnect the server. It calls <code>ServerThread</code>'s
	 * <code>disconnect()</code> method.
	 * 
	 * @throws IOException
	 *             if an input/output operation is interrupted.
	 */
	public void disconnect() throws IOException {
		server.disconnect();
	}

	public void kickUser(String client) {
		ClientThread clientThread = ServerThread.clients.get(client);
		clientThread.sendMessage(new Message(Message.SERVER_NICK, Message.KICK));
		clientThread.removeClient();
		StringBuffer sb = new StringBuffer();
		sb.append(clientThread.getName());
		sb.append(Message.KICK2);
		clientThread.reSendAll(new Message(Message.SERVER_NICK, sb.toString()));
		clientThread.disconnect();
	}
}
