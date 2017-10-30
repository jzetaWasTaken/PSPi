package logic;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;

public class ThreadLabel implements Runnable {
	private JLabel label;
	private String name;
	Thread t;
	
	public ThreadLabel(JLabel label, String name) {
		this.label = label;
		this.name = name;
		t = new Thread(this);
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}
	
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
			Color color;
			while (true) {
				color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
				this.label.setForeground(color);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.out.println("Thread " + this.getName() + " has finished");
		}
	}
	
	public void stopThread() {
		t.interrupt();
	}
}
