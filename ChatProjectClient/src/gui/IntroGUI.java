package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class IntroGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textNickName;
	private JLabel lblError;
	private JButton btnAccept;

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

		btnAccept.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		
	}
}
