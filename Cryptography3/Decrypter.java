package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.awt.event.ActionEvent;

public class Decrypter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ALG_ENC = "AES/CBC/PKCS5Padding";
	private static final String ALG = "PBKDF2WithHmacSHA1";
	private static final String SALT = "1111111111111111";
	private static final String AES = "AES";
	private static final String PASSW_ERROR = "Wrong password, motherfucker!";
	private JPanel contentPane;
	private JTextField tfPath;
	private JTextArea taMessage;
	private JButton btnDecrypt;
	private JPasswordField pfKey;
	private JButton btnChooseFile;
	private File file = null;
	JFrame mySelf = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decrypter frame = new Decrypter();
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
	public Decrypter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChooseFile = new JLabel("Choose file:");
		lblChooseFile.setBounds(10, 11, 57, 14);
		contentPane.add(lblChooseFile);
		
		tfPath = new JTextField();
		tfPath.setBounds(77, 8, 307, 20);
		contentPane.add(tfPath);
		tfPath.setColumns(10);
		tfPath.setEditable(false);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 36, 57, 14);
		contentPane.add(lblPassword);
		
		taMessage = new JTextArea();
		taMessage.setBounds(10, 71, 414, 143);
		contentPane.add(taMessage);
		taMessage.setEditable(false);
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(335, 228, 89, 23);
		contentPane.add(btnDecrypt);
		btnDecrypt.setEnabled(false);
		
		pfKey = new JPasswordField();
		pfKey.setBounds(77, 33, 140, 20);
		contentPane.add(pfKey);
		
		btnChooseFile = new JButton("...");
		btnChooseFile.setBounds(394, 7, 30, 23);
		contentPane.add(btnChooseFile);
		
		pfKey.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (!(pfKey.getPassword().length == 0) && !(tfPath.getText().trim().equals(""))) {
					btnDecrypt.setEnabled(true);
				} else {
					btnDecrypt.setEnabled(false);
				}
			}
		});
		
		btnChooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = chooser.showDialog(chooser, "Accept");
				if (option == 0) {
					file = chooser.getSelectedFile();
					tfPath.setText(file.getAbsolutePath());
				} else {
					tfPath.setText(null);
					btnDecrypt.setEnabled(false);
				}
			}
		});
		
		btnDecrypt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String  privKeyString = new String(pfKey.getPassword());
				byte [] salt = SALT.getBytes();
				KeySpec spec = new PBEKeySpec(privKeyString.toCharArray(), salt, 65536, 128);
				try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
					SecretKeyFactory factory = SecretKeyFactory.getInstance(ALG);
					byte [] key = factory.generateSecret(spec).getEncoded();
					SecretKey privKey = new SecretKeySpec(key, 0, key.length, AES);
					
					byte [] iv = (byte []) in.readObject();
					byte [] encodedMessage = (byte []) in.readObject();
					
					Cipher cipher = Cipher.getInstance(ALG_ENC);
					IvParameterSpec ivParam = new IvParameterSpec(iv);
					cipher.init(Cipher.DECRYPT_MODE, privKey, ivParam);
					byte [] decodedMessage = cipher.doFinal(encodedMessage);
					
					taMessage.setText(new String(decodedMessage));
					
					tfPath.setText(null);
					file = null;
					pfKey.setText(null);
					btnDecrypt.setEnabled(false);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					JOptionPane.showMessageDialog(mySelf, PASSW_ERROR);
				}
			}
		});
	}
}
