package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerThreadReceive implements Runnable {

	public static final String EXIT = "exit";
	private Socket client;
	
	public ServerThreadReceive(Socket client) {
		this.client = client;
		new Thread(this).start();
	}

	@Override
	public void run() {
		InputStream is = null;
		try {
			is = new ObjectInputStream(client.getInputStream());
			while (true) {
				String msg = (String)((ObjectInputStream)is).readObject();
				System.out.println(msg);
				if (msg.equals(EXIT))
					System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
