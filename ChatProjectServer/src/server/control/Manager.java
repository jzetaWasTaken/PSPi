package server.control;

import java.io.IOException;
import javax.swing.JTextArea;

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
	public void startCommunication(JTextArea textArea) {
		server = new ServerThread(textArea);
	}

	/**
	 * Method to disconnect the server. It calls <code>ServerThread</code>'s
	 * <code>disconnect()</code> method.
	 * 
	 * @throws IOException
	 * 			if an input/output operation is interrupted.
	 */
	public void disconnect() throws IOException {
		server.disconnect();
	}
}
