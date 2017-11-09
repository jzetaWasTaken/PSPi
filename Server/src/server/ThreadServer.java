package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadServer implements Runnable{

	private Socket client;
	public static final int PACKAGE_SIZE = 2048;
	
	public ThreadServer(Socket client) {
		this.client = client;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		OutputStream out = null;
		InputStream in = null;
		try {
			out = new DataOutputStream(client.getOutputStream());
			File file = new File("./nice_picture.jpg");
			int size = (int) file.length();
			((DataOutputStream)out).writeInt(size);
			
			in = new FileInputStream("./nice_picture.jpg");
			
			int times = (int) (size/PACKAGE_SIZE);
			int remainder = (int) (size - times * PACKAGE_SIZE);
			byte readBytes[] = new byte[PACKAGE_SIZE];
			
			for (int i = 0; i < times; i++) {
				in.read(readBytes);
				out.write(readBytes);
			}
			
			if (remainder != 0) {
				in.read(readBytes, 0, remainder);
				out.write(readBytes, 0, remainder);
			}
 		} catch (FileNotFoundException e) {
			e.printStackTrace();
 		} catch (Exception e) {
 			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (client != null)
					client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
