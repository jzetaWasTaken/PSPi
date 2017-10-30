package logic.threads;

import java.awt.Color;

import javax.swing.JLabel;

public class ThreadBlinker extends Thread {

	ThreadClock tc;
	JLabel lblTime;
	
	public ThreadBlinker(ThreadClock threadClock, JLabel lblTime) {
		tc = threadClock;
		this.lblTime = lblTime;
		this.start();
	}

	@Override
	public void run() {
		try {
			tc.join();
			while (1 < 2) {
				Color color = lblTime.getForeground() == Color.BLACK ? Color.RED : Color.BLACK;
				lblTime.setForeground(color);
				sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
