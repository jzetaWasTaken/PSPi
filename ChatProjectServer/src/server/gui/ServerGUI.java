package server.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import server.control.Manager;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * Class representing the main server graphical user interface window.
 * 
 * @author Jon Zaballa
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class ServerGUI extends JFrame {

	/**
	 * A unique serial version identifier.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Text area to display chat messages.
	 */
	private JTextArea textArea;
	/**
	 * Button to exit the program.
	 */
	private JButton btnExit;
	/**
	 * <code>Manager</code> instance.
	 */
	private Manager manager = new Manager();
	/**
	 * A reference to itself;
	 */
	private final ServerGUI SERVER_GUI = this;

	/**
	 * Main method. It initializes the graphical user interface window.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructs a <code>ServerGUI</code> instance and sets its main features.
	 */
	public ServerGUI() {
		// Main window features.
		setTitle("Server window");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		setBounds(10, 10, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Text area.
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(10, 20, 422, 190);
		contentPane.add(scroll);

		// Exit button.
		btnExit = new JButton("Exit");
		btnExit.setBounds(181, 232, 89, 23);
		contentPane.add(btnExit);
		// Exit button action listener.
		btnExit.addActionListener(new ActionListener() {

			/**
			 * Action to be performed when exit button is pressed.
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					// Call manager's disconnect method.
					manager.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Close the window.
				SERVER_GUI.dispose();
			}
		});

		// Call manager's method to start communication. That is, to create the server's
		// main thread.
		this.manager.startCommunication(textArea);
	}
}
