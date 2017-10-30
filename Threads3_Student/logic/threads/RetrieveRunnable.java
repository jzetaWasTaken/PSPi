package logic.threads;

import javax.swing.JTextArea;

import dataAccess.FileManager;

public class RetrieveRunnable implements Runnable {
	
	JTextArea textArea;

	public RetrieveRunnable(JTextArea textArea) {
		this.textArea = textArea;
		new Thread(this).start();
	}

	@Override
	public void run() {
		textArea.setText(new FileManager().readFromFile());
	}

}
