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
	public static final String DIR_HOME = File.separator + "home";
	private static final String FOLDER_DOWNLOAD = "./";
	//private static final String HOST_NAME = "localhost";
	private String dirCurrent = DIR_HOME;
	public static final String DIR_LABEL = "(DIR)";
	public static final String UP_SUCCESS = "0";
	public static final String UP_CANCEL = "1";
	public static final String UP_ERROR = "-1";
	private String fileSelected = "";
	private JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<>();
	
	public Manager(JList<String> list) {
		this.list = list;
	}
	
	public void startConnection(String userName, String passw, String server) 
			throws FTPLoginException, IOException {
		client.connect(server);
		if (client.login(userName, passw)) {
			client.changeWorkingDirectory(DIR_HOME);
			fillList();
		} else {
			throw new FTPLoginException("Bad user name or password");
		}
	}
	
	public void selectListElement(String dirSelected) throws IOException {
		if (dirSelected.contains(DIR_LABEL)) {
			client.changeWorkingDirectory(dirSelected.split(" ")[1]);
			dirCurrent = client.printWorkingDirectory();
			fillList();
		} else {
			fileSelected = dirSelected;
		}
	}
	
	public void selectListFirstElement(String dirSelected) throws IOException {
		if (!dirSelected.equals(DIR_HOME)) {
			changeDirectory();
		}
	}
	
	public void fillList() throws IOException {
		model.removeAllElements();
		FTPFile [] files = client.listFiles();
		
		model.addElement(dirCurrent);
		for (int i = 0; i < files.length; i++) {
			if (!(files[i].getName()).equals(".") && !(files[i].getName()).equals("..")) {
				String name = files[i].getName();
				if (files[i].isDirectory()) {
					name = DIR_LABEL + " " + name;
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
	
	public boolean downloadFile() throws IOException {
		boolean success = false;
		String fileToDownload = FOLDER_DOWNLOAD + fileSelected;
		BufferedOutputStream out = null;
		try {
			client.setFileType(FTP.BINARY_FILE_TYPE);
			out = new BufferedOutputStream(new FileOutputStream(fileToDownload));
			if (client.retrieveFile(fileSelected, out)) 
				success = true;
		} finally {
			if (out != null)
				out.close();
		}
		return success;
	}
	
	public void exitServer() throws IOException {
		if (client.isConnected()) {
			client.logout();
			client.disconnect();
		}
	}
	
	public String uploadFile() throws IOException {
		String response = UP_ERROR;
		BufferedInputStream in = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle(CHOOSER_TITLE);
		int answer = fileChooser.showDialog(fileChooser, CHOOSER_APPROVE);
		if (answer == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			System.out.println(path);
			System.out.println(dirCurrent);
			String name = file.getName();
			try {
				client.setFileType(FTP.BINARY_FILE_TYPE);
				in = new BufferedInputStream(new FileInputStream(path));
				if (client.storeFile(dirCurrent + File.separator + name, in)) {
					fillList();
					response = UP_SUCCESS;
				} 
			} finally {
				if (in != null)
					in.close();
			}
		} else {
			response = UP_CANCEL;
		}
		return response;
	}
	
	public boolean deleteFile() throws IOException {
		return client.deleteFile(fileSelected);
	}
}
