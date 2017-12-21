package manager;


import java.io.IOException;
import java.net.SocketException;
import javax.swing.DefaultListModel;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import exceptions.FTPLoginException;

public class Manager {

	private FTPClient client = new FTPClient();
	private static final String DIR_HOME = "/home";
	private static final String FOLDER_DOWNLOAD = "./";
	private static final String HOST_NAME = "localhost";
	private String dirCurrent = DIR_HOME;
	private static final String DIR_NAME = "(DIR)";
	private String fileSelected = "";
	
	public DefaultListModel<String> startConnection(String userName, String passw) 
			throws FTPLoginException, IOException {
		client.connect(HOST_NAME);
		System.out.println(client.getReplyString());
		DefaultListModel<String> fileList = null;
		if (client.login(userName, passw)) {
			client.changeWorkingDirectory(DIR_HOME);
			fillList();
		} else {
			throw new FTPLoginException("Bad user name or password");
		}
		return fileList;
	}
	
	public void selectListElement(String dirSelected) throws IOException {
		if (dirSelected.contains(DIR_NAME)) {
			changeDirectory();
		} else {
			
		}
	}
	
	public void selectListFirstElement(String dirSelected) throws IOException {
		if (!dirSelected.equals(DIR_HOME)) {
			changeDirectory();
		}
	}
	
	private DefaultListModel<String> fillList() throws IOException {
		FTPFile [] files = client.listFiles();
		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement(dirCurrent);
		for (int i = 0; i < files.length; i++) {
			if (!(files[i].getName()).equals(".") && !(files[i].getName()).equals("..")) {
				String name = files[i].getName();
				if (files[i].isDirectory()) {
					name = DIR_NAME + " " + name;
				}
				model.addElement(name);
			}
		}
		return model;
	}

	private void changeDirectory() throws IOException {
		client.changeToParentDirectory();
		dirCurrent = client.printWorkingDirectory();
		client.changeWorkingDirectory(dirCurrent);
		fillList();
	}
}
