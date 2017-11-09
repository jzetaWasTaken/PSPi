package ui;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import exceptions.PhotoNotFoundException;

import javax.swing.JLabel;

public class PhotoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPicture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhotoWindow frame = new PhotoWindow();
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
	public PhotoWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			String path = new Client().downloadImage();
			ImageIcon photo = new ImageIcon(path);
			lblPicture = new JLabel(photo);
		} catch (PhotoNotFoundException e) {
			lblPicture = new JLabel(e.getMessage());		
		}
	
		
		lblPicture.setBounds(12, 24, 426, 264);
		contentPane.add(lblPicture);
	}

}
