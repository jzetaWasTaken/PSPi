package client.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import client.control.Manager;
import client.exceptions.NickExistsException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;

/**
 * Class that represents the login window to set the user's nickname.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class IntroGUI extends JFrame {

	/**
	 * A unique serial version identifier.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String constant for error message when the nickname is already in use.
	 */
	private static final String NICK_ERROR_MSG = "Nickname already exists";
	/**
	 * String constant for error message when the user name has not been set.
	 */
	private static final String ERROR_MSG = "Enter nick name first";
	/**
	 * String constant error message to display when server is not responding.
	 */
	private static final String SERVER_ERROR_MSG = "Server not responding";
	/**
	 * Text field to enter the user's nickname.
	 */
	private JTextField textNickName;
	/**
	 * Label to display error messages.
	 */
	private JLabel lblError;
	/**
	 * Button to accept the user nickname entered and start the chat.
	 */
	private JButton btnAccept;
	/**
	 * <code>Manager</code> instance.
	 */
	private Manager manager = new Manager();

	/**
	 * Applications main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Initialize the login window
				try {
					IntroGUI frame = new IntroGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor that creates the window and initializes its main features.
	 */
	public IntroGUI() {
		// Set window's main features.
		setTitle("Client intro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 400, 268, 166);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set the label for the nickname text field.
		JLabel lblNickText = new JLabel("Enter your nickname:");
		lblNickText.setBounds(44, 11, 166, 14);
		contentPane.add(lblNickText);

		// Initialize the nickname text field.
		textNickName = new JTextField();
		textNickName.setBounds(44, 36, 166, 20);
		contentPane.add(textNickName);
		textNickName.setColumns(10);

		// Initialize the accept button
		btnAccept = new JButton("Accept");
		btnAccept.setBounds(87, 85, 89, 23);
		contentPane.add(btnAccept);

		// Initialize the label to display errors
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(43, 60, 190, 14);
		contentPane.add(lblError);

		// Accept button's action listener.
		btnAccept.addActionListener(new ActionListener() {

			/**
			 * Get's the nickname and calls the manager's method to connect to the server.
			 * If it succeeds, disposes the current window and starts the chat's main
			 * window. If there is an error, it displays a message in the error label.
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!textNickName.getText().trim().equals("")) {
					try {
						// Call manager's method to connect to the server.
						manager.connect(textNickName.getText());

						// Create main window.
						ClientGUI clientGui = new ClientGUI(textNickName.getText(), manager);
						clientGui.setTitle(textNickName.getText());
						clientGui.setVisible(true);

						// Close login window.
						Component c = (Component) event.getSource();
						JOptionPane.getFrameForComponent(c).dispose();
					} catch (ConnectException e) {
						// Display server error message.
						lblError.setText(SERVER_ERROR_MSG);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NickExistsException e) {
						// Display nickname error message.
						lblError.setText(NICK_ERROR_MSG);
					}
				} else {
					// Display error message if no nickname has been typed.
					lblError.setText(ERROR_MSG);
				}
			}
		});
	}
}
