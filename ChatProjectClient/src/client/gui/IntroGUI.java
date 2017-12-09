package client.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.control.Manager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

public class IntroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Enter nick name first";
	private static final String SERVER_ERROR_MSG = "Server not responding";
	private JTextField textNickName;
	private JLabel lblError;
	private JButton btnAccept;
	private Manager manager = new Manager();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroGUI frame = new IntroGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public IntroGUI() {
		setTitle("Client intro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 400, 268, 166);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNickText = new JLabel("Enter your nickname:");
		lblNickText.setBounds(44, 11, 166, 14);
		contentPane.add(lblNickText);

		textNickName = new JTextField();
		textNickName.setBounds(44, 36, 166, 20);
		contentPane.add(textNickName);
		textNickName.setColumns(10);

		btnAccept = new JButton("Accept");

		btnAccept.setBounds(87, 85, 89, 23);
		contentPane.add(btnAccept);

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(43, 60, 167, 14);
		contentPane.add(lblError);

		btnAccept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!textNickName.getText().trim().equals("")) {
					try {
						manager.connect(textNickName.getText());
						ClientGUI clientGui = new ClientGUI(textNickName.getText(), manager);
						clientGui.setTitle(textNickName.getText());
						clientGui.setVisible(true);
						Component c = (Component) event.getSource();
						JOptionPane.getFrameForComponent(c).dispose();
					} catch (ConnectException e) {
						lblError.setText(SERVER_ERROR_MSG);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				} else {
					lblError.setText(ERROR_MSG);
				}
			}
		});
	}
}
