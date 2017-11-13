package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ThreadSend implements Runnable {
	
	public static final String EXIT = "exit";
	private Socket client = null;
	private static Scanner in = new Scanner(System.in);

	public ThreadSend(Socket client) {
		this.client = client;
		new Thread(this).start();
	}

	@Override
	public void run() {
		OutputStream os = null;
		String msg = null;
		try {
			os = new ObjectOutputStream(client.getOutputStream());
			while (true) {
				msg = in.nextLine();
				((ObjectOutputStream)os).writeObject(msg);
				if (msg.equals(EXIT))
					System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
