package logic.threads;

import javax.swing.JTextArea;

import dataAccess.FileManager;

public class SaveRunnable implements Runnable {

	JTextArea textArea;
	
	public SaveRunnable(JTextArea textArea) {
		this.textArea = textArea;
		new Thread(this).start();
	}

	@Override
	public void run() {
		new FileManager().writeToFile(textArea.getText());
	}
}
