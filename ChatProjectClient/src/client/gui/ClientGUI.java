package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import client.control.Manager;
import model.Message;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * Class representing the main graphical user interface window to send and
 * receive messages
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class ClientGUI extends JFrame {
	
	/**
	 * A unique serial version identifier.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Text field to write messages.
	 */
	private JTextField textField;
	/**
	 * Text area to display sent messages.
	 */
	private JTextArea textArea;
	/**
	 * Button to send messages.
	 */
	private JButton btnSend;
	/**
	 * Button to exit the program.
	 */
	private JButton btnExit;
	/**
	 * User's nickname to send along with the message.
	 */
	private String nickName;
	/**
	 * <code>Manager</code> class instance to call methods to perform main chat
	 * operations.
	 */
	private Manager manager;
	/**
	 * A reference to the <code>ClientGUI</code> instance.
	 */
	private final ClientGUI CLIENT_GUI = this;

	/**
	 * Constructor that initializes the window.
	 * 
	 * @param nickName	user's nickname
	 * @param manager	<code>Manager</code> class instance
	 * @exception IOException
	 * 				if an input/output operation is interrupted.
	 */
	public ClientGUI(String nickName, Manager manager) {
		// Initialize fields with received parameters.
		this.nickName = nickName;
		this.manager = manager;
		
		// Configure window's main features.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		Random rand = new Random();
		int x = 500 + rand.nextInt(700 + 1 - 500);
		int y = rand.nextInt(500);
		setBounds(x, y, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Initialize message text field.
		textField = new JTextField();
		textField.setBounds(10, 11, 321, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		// Initialize button to send messages.
		btnSend = new JButton("Send");
		btnSend.setBounds(341, 10, 89, 23);
		contentPane.add(btnSend);
		
		// Initialize text area to display chat messages.
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(10, 39, 420, 179);		
		contentPane.add(scroll);

		// Initialize exit button
		btnExit = new JButton("Exit");
		btnExit.setBounds(179, 232, 89, 23);
		contentPane.add(btnExit);

		// Send button action listener
		btnSend.addActionListener(new ActionListener() {
			
			/**
			 * The method get's the message and calls manager's method to send
			 * the message. It clears the text field after sending the message.
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!textField.getText().trim().equals("")) {
						try {
							manager.sendMessage(new Message(CLIENT_GUI.nickName,textField.getText()));
						} catch (SocketException e) {
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					textField.setText(null);
				}
			}
		});
		
		// Exit button action listener
		btnExit.addActionListener(new ActionListener() {
			
			/**
			 * The method calls the manager's method to disconnect the client
			 * from the server and closes the client's window.
			 * 
			 * @exception SocketException
			 * 				if there is an error accessing the socket.
			 * @exception IOException
			 *	 			if an input/output operation is interrupted.
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					manager.disconnect(nickName);
				} catch (SocketException e) {
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					CLIENT_GUI.dispose();
				}
			}
		});
		
		// Text fields key listener
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			/**
			 * Sends the message if the user presses the <b>Enter</b> key.
			 */
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!textField.getText().trim().equals("")) {
						try {
							manager.sendMessage(new Message(CLIENT_GUI.nickName,textField.getText()));
						} catch (SocketException e) {
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						textField.setText(null);
					}
				}
			}
		});
		
		// Call manager's method to create the client's listener thread
		try {
			this.manager.startCommunication(this.nickName, this.textArea, this.btnSend);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
