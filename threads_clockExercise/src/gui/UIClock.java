package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logic.ThreadClock;
import logic.ThreadLabel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIClock extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ThreadLabel tl1;
	private ThreadLabel tl2;
	private ThreadClock tc1;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton;
	private JButton btnExit;
	private JPanel contentPane;
	private JButton btnNewButton_3;
	private long[] counters;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIClock frame = new UIClock();
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
	public UIClock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("<<");
		label.setFont(new Font("Dialog", Font.BOLD, 24));
		label.setBounds(121, 83, 70, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("00:00");
		label_1.setFont(new Font("Dialog", Font.BOLD, 24));
		label_1.setBounds(191, 72, 78, 36);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel(">>");
		label_2.setFont(new Font("Dialog", Font.BOLD, 24));
		label_2.setBounds(285, 83, 70, 15);
		contentPane.add(label_2);
		
		btnNewButton = new JButton("Start");
		btnNewButton.setBounds(12, 168, 76, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tc1 = new ThreadClock(label_1, "clock");
				tc1.start();
				btnNewButton_2.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				btnNewButton.setEnabled(false);
				btnNewButton_3.setEnabled(true);
			}
		});
		
		btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.setBounds(100, 168, 76, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tc1.interrupt();
				tc1.reset();
				btnNewButton_1.setEnabled(false);
				btnNewButton.setEnabled(true);
				btnNewButton_2.setEnabled(false);
				btnNewButton_3.setEnabled(false);
			}
		});
		
		btnNewButton_2 = new JButton("Initialize");
		btnNewButton_2.setBounds(188, 168, 95, 25);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tc1.reset();
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(398, 168, 59, 25);
		contentPane.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tc1 != null) {
					tc1.interrupt();
				}
				tl2.stopThread();
				tl1.stopThread();
				System.exit(0);
			}
		});
		
		tl1 = new ThreadLabel(label, "tLabel1");
		tl2 = new ThreadLabel(label_2, "tLabel2");
		
		btnNewButton_3 = new JButton("Pause");
		btnNewButton_3.setBounds(295, 168, 95, 25);
		contentPane.add(btnNewButton_3);
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnNewButton_3.getText() == "Pause") {
					btnNewButton_3.setText("Resume");
					counters = tc1.getCounters();
					tc1.interrupt();
				} else {
					btnNewButton_3.setText("Pause");
					tc1 = new ThreadClock(label_1, "clock");
					tc1.resumeThread(counters);
				}
			}
		});
	}
}
