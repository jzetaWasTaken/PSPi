package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logic.AppManager;

public class Window extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton btnSave;
	private JButton btnClean;
	private JButton btnRetrieve;
	private JLabel lblTime;
	private JButton btnExit;
	// TODO

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 423);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.NORTH);
		panel1.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblTitle = new JLabel("* * * * * QUICK CREATIVE WRITING * * * * *");
		lblTitle.setForeground(new Color(75, 0, 130));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel1.add(lblTitle);
		
		JLabel lblInstructions = new JLabel("Instructions: 10 minutes to write. Text will be autosaved every 2 minutes.");
		panel1.add(lblInstructions);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.WEST);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textArea.setPreferredSize(new Dimension(300, 280));
		panel2.add(textArea);
		textArea.setLineWrap(true);
		
		JPanel panel3 = new JPanel();
		contentPane.add(panel3, BorderLayout.EAST);
		GridBagLayout gbl_panel3 = new GridBagLayout();
		gbl_panel3.columnWidths = new int[]{0, 0};
		gbl_panel3.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel3.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel3.setLayout(gbl_panel3);

		JLabel lblTimeLeft = new JLabel("Time left");
		lblTimeLeft.setForeground(new Color(0, 100, 0));
		lblTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblTimeLeft = new GridBagConstraints();
		gbc_lblTimeLeft.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeLeft.gridx = 0;
		gbc_lblTimeLeft.gridy = 0;
		panel3.add(lblTimeLeft, gbc_lblTimeLeft);
		
		lblTime = new JLabel("00:00");
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.insets = new Insets(0, 0, 5, 0);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 1;
		panel3.add(lblTime, gbc_lblTime);
		
		btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 3;
		panel3.add(btnSave, gbc_btnSave);
		
		btnClean = new JButton("Clean");
		GridBagConstraints gbc_btnClean = new GridBagConstraints();
		gbc_btnClean.insets = new Insets(0, 0, 5, 0);
		gbc_btnClean.gridx = 0;
		gbc_btnClean.gridy = 4;
		panel3.add(btnClean, gbc_btnClean);
		
		btnRetrieve = new JButton("Retrieve last saved");
		GridBagConstraints gbc_btnRetrieve = new GridBagConstraints();
		gbc_btnRetrieve.gridx = 0;
		gbc_btnRetrieve.gridy = 5;
		panel3.add(btnRetrieve, gbc_btnRetrieve);
		
		JPanel panel4 = new JPanel();
		contentPane.add(panel4, BorderLayout.SOUTH);
		panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnExit = new JButton("Exit application");
		panel4.add(btnExit);
		
		btnExit.addActionListener(this);
		btnSave.addActionListener(this);
		btnClean.addActionListener(this);
		btnRetrieve.addActionListener(this);
		
		new AppManager().startCountdown(textArea, lblTime);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton) e.getSource();		
		if (boton == btnExit) {
			new AppManager().exit();
			
		} else if(boton == btnSave) {
			new AppManager().saveText(textArea);
			
		} else if(boton == btnClean) {
			textArea.setText("");
			
		} else if(boton == btnRetrieve) {
			System.out.println("Retrieving");
			new AppManager().getText(textArea);
		}		
	}
}
