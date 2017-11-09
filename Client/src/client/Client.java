package client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import exceptions.PhotoNotFoundException;

public class Client {

	public final static int PACKAGE_SIZE = 2048;
	public final static String IP = "127.0.0.1";
	public final static int PORT = 5002;
	
	public String downloadImage() throws PhotoNotFoundException {
		Socket client = null;
		OutputStream out = null;
		InputStream in = null;
		FileOutputStream fOut = null;
		String path;
		try {
			client = new Socket(IP, PORT);
			in = new DataInputStream(client.getInputStream());
			
			int size = ((DataInputStream)in).readInt();
			
			int times = (int) (size/PACKAGE_SIZE);
			int reminder = (int) (size-times*PACKAGE_SIZE);
			
			byte readbytes[] = new byte[PACKAGE_SIZE];
			
			path = "./nice_picture.jpg";
			fOut = new FileOutputStream(path);
			
			for (int i = 0; i < times; i++) {
				in.read(readbytes);
				fOut.write(readbytes);
			}
			
			if (reminder != 0) {
				in.read(readbytes, 0, reminder);
				fOut.write(readbytes, 0, reminder);
			}
			
		} catch (IOException e) {
			throw new PhotoNotFoundException("Photo not found");
		} finally {
			try {
				if (client!=null)
					client.close();
				if(out!=null)
					out.close();
				if(in!=null)
					in.close();
				if(fOut!=null)
					fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}
}
