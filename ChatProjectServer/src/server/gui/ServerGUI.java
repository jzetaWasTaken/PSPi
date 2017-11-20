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
import java.awt.event.ActionEvent;

public class ServerGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton btnExit;
	private Manager manager;
	
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

	public ServerGUI() {
		setTitle("Server window");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		setBounds(10, 10, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(10, 20, 422, 190);
		contentPane.add(scroll);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setBounds(181, 232, 89, 23);
		contentPane.add(btnExit);
	}

	public void actionPerformed(ActionEvent event) {
		
	}
}
