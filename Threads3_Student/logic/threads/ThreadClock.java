package logic.threads;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ThreadClock extends Thread{
	private int secCounter = 600;
	JLabel lblTime;
	JTextArea textArea;
	
	public ThreadClock(JLabel lblTime, JTextArea textArea) {
		this.lblTime = lblTime;
		this.textArea = textArea;
		this.start();
	}
	
	@Override
	public void run() {
		try {
			while (secCounter >= 0) {
				System.out.println(secCounter);
				displayClock();
				sleep(1000);
				secCounter--;
			}
			textArea.setFocusable(false);
			textArea.setEnabled(false);
			textArea.setText("Time has expired");
		} catch (InterruptedException e) {

		}
	}
	
	private void displayClock() {
		lblTime.setText(String.format("%02d", secCounter/60) + ":" + String.format("%02d", secCounter%60));
	}
}
