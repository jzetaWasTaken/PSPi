package server.gui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import server.control.Manager;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
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
	
	private JList<String> userList;
	
	private JButton btnKick;
	
	private DefaultListModel<String> model;
	
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
		setBounds(10, 10, 650, 300);
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
		
		// User list
		model = new DefaultListModel<>();
		
		userList = new JList<>(model);
		
		JScrollPane listScrool = new JScrollPane(userList);
		listScrool.setBounds(440, 20, 200, 190);
		contentPane.add(listScrool);
		
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Exit button.
		btnExit = new JButton("Exit");
		btnExit.setBounds(181, 232, 89, 23);
		contentPane.add(btnExit);
		
		// Kick user button
		btnKick = new JButton("Kick");
		btnKick.setBounds(490, 232, 89, 23);
		btnKick.setEnabled(false);
		contentPane.add(btnKick);
		
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
		
		// Kick button action listener.
		btnKick.addActionListener(new ActionListener() {
			
			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.kickUser(userList.getSelectedValue());
			}
		});
		
		// JList listener
		userList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (userList.getSelectedIndex() == -1) {
					btnKick.setEnabled(false);
				} else {
					btnKick.setEnabled(true);
				}
			}
		});

		// Call manager's method to start communication. That is, to create the server's
		// main thread.
		this.manager.startCommunication(textArea, model);
	}
}
