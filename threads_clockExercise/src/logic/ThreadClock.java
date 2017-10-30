package logic;

import javax.swing.JLabel;

public class ThreadClock extends Thread {
	private JLabel labelClock;
	private long secCounter = 0;
	private long minCounter = 0;
	private volatile boolean keepRunning = true;
	
	public ThreadClock(JLabel label, String name) {
		super.setName(name);
		this.labelClock = label;
		this.setPriority(Thread.NORM_PRIORITY);
	}
	
	public JLabel getLabelClock() {
		return labelClock;
	}
	public void setLabelClock(JLabel labelClock) {
		this.labelClock = labelClock;
	}
	
	@Override
	public void run() {
		try {
			while (keepRunning) {
				display();
				sleep(10);
				secCounter++;
				if ((secCounter%60) == 0) {
					minCounter++;
				}
			}
		} catch (Exception e) {
			System.out.println("Thread " + this.getName() + " has finished");
		}
	}
	
	public void initialize() {
		
	}
	
	private void display() {
		labelClock.setText(String.format("%02d", minCounter%60) + 
				":" +
				String.format("%02d", secCounter%60));
	}
	
	public void stopThread() {
		this.keepRunning = false;
	}

	public void reset() {
		secCounter = 0;
		minCounter = 0;
		this.display();
	}

	public long[] getCounters() {
		long [] counters = {secCounter, minCounter};
		return counters;
	}

	public void resumeThread(long [] counters) {
		secCounter = counters[0];
		minCounter = counters[1];
		this.start();
	}
}
