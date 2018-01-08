package manager;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import exceptions.FTPLoginException;

public class Manager {

	private FTPClient client = new FTPClient();
	private static final String CHOOSER_TITLE = "Select file to upload";
	private static final String CHOOSER_APPROVE = "Upload";
	private static final String DIR_HOME = "/home";
	private static final String FOLDER_DOWNLOAD = "./";
	private static final String HOST_NAME = "localhost";
	private String dirCurrent = DIR_HOME;
	private static final String DIR_NAME = "(DIR)";
	private String fileSelected = "";
	private JList<String> list;
	
	public Manager(JList<String> list) {
		this.list = list;
	}
	
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
	
	public boolean selectListElement(String dirSelected) throws IOException {
		boolean isDir = false;
		if (dirSelected.contains(DIR_NAME)) {
			changeDirectory();
			isDir = true;
		} else {
			fileSelected = dirSelected; 
		}
		return isDir;
	}
	
	public void selectListFirstElement(String dirSelected) throws IOException {
		if (!dirSelected.equals(DIR_HOME)) {
			changeDirectory();
		}
	}
	
	private void fillList() throws IOException {
		list.removeAll();
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
		list.setModel(model);
	}

	private void changeDirectory() throws IOException {
		client.changeToParentDirectory();
		dirCurrent = client.printWorkingDirectory();
		client.changeWorkingDirectory(dirCurrent);
		fillList();
	}
	
	public void downloadFile() throws IOException {
		String fileToDownload = FOLDER_DOWNLOAD + fileSelected;
		BufferedOutputStream out = null;
		try {
			client.setFileType(FTP.BINARY_FILE_TYPE);
			out = new BufferedOutputStream(new FileOutputStream(fileToDownload));
			if (client.retrieveFile(fileSelected, out)) {
				// Successful
			} else {
				// Error
			}
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	public void exitServer() throws IOException {
		client.logout();
		client.disconnect();
	}
	
	public void uploadFile() throws IOException {
		BufferedInputStream in = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle(CHOOSER_TITLE);
		int answer = fileChooser.showDialog(fileChooser, CHOOSER_APPROVE);
		if (answer == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			String name = file.getName();
			try {
				client.setFileType(FTP.BINARY_FILE_TYPE);
				in = new BufferedInputStream(new FileInputStream(path));
				if (client.storeFile(dirCurrent, in)) {
					// Successful
					fillList();
				} else {
					throw new IOException();
				}
			} finally {
				if (in != null)
					in.close();
			}
		}
	}
	
	public void deleteFile() throws IOException {
		client.deleteFile(fileSelected);
		fillList();
	}
}
