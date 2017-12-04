package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnSend;
	private JButton btnExit;
	private String nickName;
	private Manager manager;
	private final ClientGUI CLIENT_GUI = this;

	public ClientGUI(String nickName, Manager manager) {
		this.nickName = nickName;
		this.manager = manager;
		
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

		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.sendMessage(new Message(CLIENT_GUI.nickName,textField.getText()));
				textField.setText(null);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					manager.sendMessage(new Message(CLIENT_GUI.nickName,textField.getText()));
					textField.setText(null);
				}
			}
		});
		
		this.manager.startCommunication(this.nickName, this.textArea, this.btnSend);
	}
}
