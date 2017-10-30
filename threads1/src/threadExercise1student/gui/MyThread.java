package threadExercise1student.gui;

import javax.swing.JLabel;

public class MyThread extends Thread {
	
	private volatile boolean keepRunning = true;
	private JLabel counter;
	private JLabel priority;
	
	public MyThread(JLabel counter, JLabel priority) {
		this.counter = counter;
		this.priority = priority;
		this.start();
	}
	
	@Override
	public void run() {
		int i = 0;
		while(keepRunning) {
			counter.setText(Integer.toString(i++));
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public void printPriority(int priority) {
		this.priority.setText(Integer.toString(priority));
	}
	
	public void stopThread() {
		keepRunning = false;
	}
}
