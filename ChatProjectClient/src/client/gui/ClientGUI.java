package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import client.control.Manager;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnSend;
	private JButton btnExit;
	private String nickName;
	private Manager manager;

	public ClientGUI() {
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

		textField = new JTextField();
		textField.setBounds(10, 11, 321, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.setBounds(341, 10, 89, 23);
		contentPane.add(btnSend);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(10, 39, 420, 179);		
		contentPane.add(scroll);

		btnExit = new JButton("Exit");
		btnExit.setBounds(179, 232, 89, 23);
		contentPane.add(btnExit);

		btnSend.addActionListener(this);
		btnExit.addActionListener(this);
	
	}

	public void actionPerformed(ActionEvent event) {
		
	}
}
