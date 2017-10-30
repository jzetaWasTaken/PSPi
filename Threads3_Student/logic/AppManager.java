package logic;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import logic.threads.RetrieveRunnable;
import logic.threads.SaveRunnable;
import logic.threads.ThreadBlinker;
import logic.threads.ThreadClock;
import logic.threads.ThreadSave;

public class AppManager {
			
	public void startCountdown(JTextArea textArea, JLabel lblTime) {
		new ThreadSave(textArea);
		new ThreadBlinker(new ThreadClock(lblTime, textArea), lblTime);
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public void saveText(JTextArea textArea) {
		new SaveRunnable(textArea);
	}
	
	public void getText(JTextArea textArea) {
		new RetrieveRunnable(textArea);
	}
}
