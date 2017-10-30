package dataAccess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

	private static final String FILE_NAME = "./file.txt";

	public synchronized String readFromFile() {
		BufferedReader reader = null;
		String line = "";
		StringBuffer buffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append("\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	public synchronized void writeToFile(String textToWrite) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(FILE_NAME));
			writer.write(textToWrite);
			System.out.println("written");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) 
				writer.close();
		}
	}
}
