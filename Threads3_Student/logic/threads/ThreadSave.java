package logic.threads;

import javax.swing.JTextArea;

import dataAccess.FileManager;

public class ThreadSave extends Thread {
	
	private JTextArea textArea;
	
	public ThreadSave(JTextArea textArea) {
		this.textArea = textArea;
		this.start();
	}
	
	public void run() {
		try {
			while (true) {
				sleep(120000);
				new FileManager().writeToFile(textArea.getText());
			}
		} catch (InterruptedException e) {

		}
	}
}
