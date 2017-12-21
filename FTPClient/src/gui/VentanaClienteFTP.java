package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;


import java.awt.Color;

public class VentanaClienteFTP extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JTextField tServidor;
	private JTextField tUsuario;
	private JPasswordField tClave;
	private JButton btnConectar;
	private JButton btnSubir;
	private JButton btnDescargar;
	private JButton btnSalir;
	private JLabel label1;
	private JLabel label2;
	private JList<String> lista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaClienteFTP frame = new VentanaClienteFTP();
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
	public VentanaClienteFTP() {
		super("CLIENTE BÁSICO FTP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 482);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblServidorFtp = new JLabel("Servidor FTP:");
		lblServidorFtp.setBounds(242, 14, 86, 14);
		contentPane.add(lblServidorFtp);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 19, 74, 14);
		contentPane.add(lblUsuario);

		tServidor = new JTextField("127.0.0.1");
		tServidor.setBounds(326, 11, 86, 20);
		contentPane.add(tServidor);
		tServidor.setColumns(10);

		tUsuario = new JTextField("usu1");
		tUsuario.setBounds(75, 13, 86, 20);
		contentPane.add(tUsuario);
		tUsuario.setColumns(10);

		JLabel lblClave = new JLabel("Clave:");
		lblClave.setBounds(10, 47, 74, 14);
		contentPane.add(lblClave);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(242, 41, 170, 23);
		contentPane.add(btnConectar);

		btnSubir = new JButton("Subir");
		btnSubir.setEnabled(false);
		btnSubir.setBounds(310, 96, 102, 23);
		contentPane.add(btnSubir);

		btnDescargar = new JButton("Descargar");
		btnDescargar.setEnabled(false);
		btnDescargar.setBounds(310, 130, 105, 23);
		contentPane.add(btnDescargar);

		label1 = new JLabel("-");
		label1.setBounds(10, 381, 388, 14);
		contentPane.add(label1);

		label2 = new JLabel("-");
		label2.setForeground(Color.RED);
		label2.setBounds(10, 406, 388, 14);
		contentPane.add(label2);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(310, 164, 105, 23);
		contentPane.add(btnSalir);

		tClave = new JPasswordField();
		tClave.setBounds(75, 44, 86, 20);
		contentPane.add(tClave);

		lista = new JList<String>();
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollBar = new JScrollPane(lista);
		scrollBar.setBounds(10, 92, 270, 278);
		contentPane.add(scrollBar);

		btnConectar.addActionListener(this);
		btnDescargar.addActionListener(this);
		btnSubir.addActionListener(this);
		btnSalir.addActionListener(this);

		lista.addMouseListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton) e.getSource();
		if (boton == btnConectar) {
			
		} else if (boton == btnSubir) {
			

		} else if (boton == btnDescargar) {
			

		} else if (boton == btnSalir) {
			
		}
	}

	

	
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
