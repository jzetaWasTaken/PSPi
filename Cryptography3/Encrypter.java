package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Encrypter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ALG_ENC = "AES/CBC/PKCS5Padding";
	private static final String ALG = "PBKDF2WithHmacSHA1";
	private static final String SALT = "1111111111111111";
	private static final String AES = "AES";
	private static final String FILE_NAME = "encoded_message";
	private JPanel contentPane;
	private JButton btnEncrypt;
	private JTextArea taMessage;
	private JPasswordField pfKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encrypter frame = new Encrypter();
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
	public Encrypter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTextToEncrypt = new JLabel("Text to encrypt");
		lblTextToEncrypt.setBounds(10, 11, 100, 14);
		contentPane.add(lblTextToEncrypt);
		
		JLabel lblPrivateKey = new JLabel("Private Key");
		lblPrivateKey.setBounds(182, 11, 65, 14);
		contentPane.add(lblPrivateKey);
		
		taMessage = new JTextArea();
		taMessage.setBounds(10, 52, 414, 158);
		contentPane.add(taMessage);
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(335, 228, 89, 23);
		contentPane.add(btnEncrypt);
		btnEncrypt.setEnabled(false);
		
		pfKey = new JPasswordField();
		pfKey.setBounds(257, 8, 167, 20);
		contentPane.add(pfKey);
		
		btnEncrypt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String  privKeyString = new String(pfKey.getPassword());
				String message = taMessage.getText();
				byte [] salt = SALT.getBytes();
				KeySpec spec = new PBEKeySpec(privKeyString.toCharArray(), salt, 65536, 128);
				try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
					SecretKeyFactory factory = SecretKeyFactory.getInstance(ALG);
					byte [] key = factory.generateSecret(spec).getEncoded();
					SecretKey privKey = new SecretKeySpec(key, 0, key.length, AES);
					Cipher cipher = Cipher.getInstance(ALG_ENC);
					cipher.init(Cipher.ENCRYPT_MODE, privKey);
					byte [] encodedMessage = cipher.doFinal(message.getBytes());
					byte [] iv = cipher.getIV();
					out.writeObject(iv);
					out.writeObject(encodedMessage);
					taMessage.setText(null);
					pfKey.setText(null);
					btnEncrypt.setEnabled(false);
					System.out.println("Message cyphed and saved");
 				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		
		pfKey.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (!taMessage.getText().trim().equals("") && !(pfKey.getPassword().length == 0)) {
					btnEncrypt.setEnabled(true);
				} else {
					btnEncrypt.setEnabled(false);
				}
			}
		});
		
		taMessage.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (!taMessage.getText().trim().equals("") && !(pfKey.getPassword().length == 0)) {
					btnEncrypt.setEnabled(true);
				} else {
					btnEncrypt.setEnabled(false);
				}
			}
		});
	}
}
